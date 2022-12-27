package com.jeanbarrossilva.ongoing.feature.activityediting.infra.inmemory

import android.content.Context
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualStatus
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.SessionManager
import com.jeanbarrossilva.ongoing.core.session.extensions.session
import com.jeanbarrossilva.ongoing.feature.activityediting.ActivityEditingModel
import com.jeanbarrossilva.ongoing.feature.activityediting.domain.EditingActivity
import com.jeanbarrossilva.ongoing.feature.activityediting.domain.EditingStatus
import com.jeanbarrossilva.ongoing.feature.activityediting.extensions.toEditingStatus
import com.jeanbarrossilva.ongoing.feature.activityediting.infra.ActivityEditingGateway
import com.jeanbarrossilva.ongoing.platform.loadable.Loadable
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.emit
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.ifLoaded
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.loadableFlow
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.toSerializableList
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.valueOrNull
import com.jeanbarrossilva.ongoing.platform.loadable.type.SerializableList
import kotlinx.coroutines.flow.Flow
import java.lang.ref.WeakReference

internal class InMemoryActivityEditingGateway(
    private val sessionManager: SessionManager,
    private val mode: EditingMode,
    private val contextRef: WeakReference<Context>
): ActivityEditingGateway {
    private val context
        get() = contextRef.get()

    constructor(
        sessionManager: SessionManager,
        mode: EditingMode,
        context: Context
    ): this(sessionManager, mode, WeakReference(context))

    override suspend fun getActivity(): Flow<Loadable<EditingActivity>> {
        return loadableFlow {
            context?.let { context ->
                mode.getActivity(context).valueOrNull?.let { activity ->
                    emit(activity)
                }
            }
        }
    }

    override suspend fun isChanged(activity: EditingActivity): Flow<Loadable<Boolean>> {
        return loadableFlow {
            context?.let {
                mode.getActivity(it).ifLoaded {
                    emit(!equals(activity))
                }
            }
        }
    }

    override suspend fun isValid(activity: EditingActivity): Flow<Loadable<Boolean>> {
        return loadableFlow {
            emit(ActivityEditingModel.isNameValid(activity.name))
        }
    }

    override suspend fun getAvailableStatuses(): Flow<Loadable<SerializableList<EditingStatus>>> {
        return loadableFlow {
            context?.let { context ->
                ContextualStatus.values
                    .map { contextualStatus -> contextualStatus.toEditingStatus(context) }
                    .toSerializableList()
                    .let { emit(it) }
            }
        }
    }

    override suspend fun edit(activity: EditingActivity) {
        sessionManager.session<Session.SignedIn>()?.userId?.let {
            mode.save(it, activity)
        }
    }
}