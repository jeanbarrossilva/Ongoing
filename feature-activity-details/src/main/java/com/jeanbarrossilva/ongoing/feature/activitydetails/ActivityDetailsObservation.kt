package com.jeanbarrossilva.ongoing.feature.activitydetails

import android.content.Context
import com.jeanbarrossilva.ongoing.context.registry.extensions.toContextualActivity
import com.jeanbarrossilva.ongoing.context.registry.extensions.contextualize
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.registry.observation.Change
import com.jeanbarrossilva.ongoing.core.registry.observation.Observation
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.user.UserRepository
import java.lang.ref.WeakReference
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first

internal class ActivityDetailsObservation(
    private val contextRef: WeakReference<Context>,
    private val session: Session,
    private val userRepository: UserRepository,
    private val activityRegistry: ActivityRegistry
): Observation {
    private val context
        get() = contextRef.get()

    constructor(
        context: Context,
        session: Session,
        userRepository: UserRepository,
        activityRegistry: ActivityRegistry
    ): this(WeakReference(context), session, userRepository, activityRegistry)

    override suspend fun onChange(change: Change, activityId: String) {
        val contextualChange = change.contextualize()
        val activity = activityRegistry
            .getActivityById(activityId)
            .filterNotNull()
            .first()
            .toContextualActivity(session, userRepository)
        context?.let {
            ActivityDetailsNotifier.onChange(it, contextualChange, activity)
        }
    }
}