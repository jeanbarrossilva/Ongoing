package com.jeanbarrossilva.ongoing.feature.activitydetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jeanbarrossilva.ongoing.context.registry.extensions.toContextualActivity
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.registry.observation.Observation
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.user.UserRepository
import com.jeanbarrossilva.ongoing.feature.activitydetails.extensions.orEmpty
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.filterIsLoaded
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.loadable
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ActivityDetailsViewModel private constructor(
    private val session: Session,
    private val userRepository: UserRepository,
    private val activityRegistry: ActivityRegistry,
    private val observation: Observation,
    private val activityId: String?
): ViewModel() {
    internal val activity = flow {
        activityId
            ?.let { activityRegistry.getActivityById(it) }
            .orEmpty
            .filterNotNull()
            .map { it.toContextualActivity(session, userRepository) }
            .loadable()
            .collect(::emit)
    }

    fun setObserving(isObserving: Boolean, onDone: () -> Unit = { }) {
        viewModelScope.launch {
            val activity = activity.filterIsLoaded().first().value
            with(activityRegistry.observer) {
                session.getUser().first()?.let {
                    if (isObserving) {
                        attach(it.id, activity.id, observation)
                    } else {
                        detach(it.id, activity.id)
                    }
                    onDone()
                }
            }
        }
    }

    companion object {
        fun createFactory(
            session: Session,
            userRepository: UserRepository,
            activityRegistry: ActivityRegistry,
            observation: Observation,
            activityId: String?
        ): ViewModelProvider.Factory {
            return viewModelFactory {
                addInitializer(ActivityDetailsViewModel::class) {
                    ActivityDetailsViewModel(
                        session,
                        userRepository,
                        activityRegistry,
                        observation,
                        activityId
                    )
                }
            }
        }
    }
}