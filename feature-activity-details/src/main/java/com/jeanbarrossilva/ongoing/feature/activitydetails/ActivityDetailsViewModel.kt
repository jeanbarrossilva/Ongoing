package com.jeanbarrossilva.ongoing.feature.activitydetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.context.registry.extensions.toContextualActivity
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.registry.observation.Observation
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.user.UserRepository
import com.jeanbarrossilva.ongoing.feature.activitydetails.extensions.toggle
import com.jeanbarrossilva.ongoing.platform.loadable.Loadable
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.filterIsLoaded
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.loadableFlowOf
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.toLoadable
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ActivityDetailsViewModel private constructor(
    private val session: Session,
    private val userRepository: UserRepository,
    private val activityRegistry: ActivityRegistry,
    private val observation: Observation,
    private val activityId: String?
): ViewModel() {
    private val activity = loadableFlowOf<ContextualActivity>()

    init {
        fetch()
    }

    fun getActivity(): StateFlow<Loadable<ContextualActivity>> {
        return activity.asStateFlow()
    }

    fun setObserving(isObserving: Boolean, onDone: () -> Unit = { }) {
        viewModelScope.launch {
            val activity = activity.filterIsLoaded().first().value
            session.getUser().first()?.let {
                activityRegistry.observer.toggle(it, activity, isObserving, observation)
                onDone()
            }
        }
    }

    private fun fetch() {
        activityId?.let {
            viewModelScope.launch {
                activity.value = activityRegistry
                    .getActivityById(it)
                    ?.toContextualActivity(session, userRepository)
                    .toLoadable()
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