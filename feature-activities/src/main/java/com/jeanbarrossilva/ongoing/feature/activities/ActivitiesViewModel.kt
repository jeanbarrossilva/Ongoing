package com.jeanbarrossilva.ongoing.feature.activities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jeanbarrossilva.ongoing.context.registry.extensions.mapToContextualActivity
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.user.UserRepository
import com.jeanbarrossilva.ongoing.platform.loadable.Loadable
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.loadableFlow
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.toSerializableList
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class ActivitiesViewModel private constructor(
    private val session: Session,
    private val userRepository: UserRepository,
    private val activityRegistry: ActivityRegistry
): ViewModel() {
    internal val user = flow { emitAll(session.getUser()) }
    internal val activities = loadableFlow {
        activityRegistry
            .getActivities()
            .map { it.mapToContextualActivity(userRepository).toSerializableList() }
            .map { Loadable.Loaded(it) }
            .collect(::emit)
    }

    companion object {
        fun createFactory(
            session: Session,
            userRepository: UserRepository,
            activityRegistry: ActivityRegistry
        ): ViewModelProvider.Factory {
            return viewModelFactory {
                addInitializer(ActivitiesViewModel::class) {
                    ActivitiesViewModel(session, userRepository, activityRegistry)
                }
            }
        }
    }
}