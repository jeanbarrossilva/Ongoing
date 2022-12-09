package com.jeanbarrossilva.ongoing.feature.activitydetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.fetcher.ContextualActivitiesFetcher
import com.jeanbarrossilva.ongoing.context.registry.extensions.getActivity
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.registry.observation.Observation
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.feature.activitydetails.extensions.toggle
import com.jeanbarrossilva.ongoing.platform.loadable.Loadable
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.ifLoaded
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ActivityDetailsViewModel private constructor(
    private val session: Session,
    private val activityRegistry: ActivityRegistry,
    private val observation: Observation,
    private val fetcher: ContextualActivitiesFetcher,
    activityId: String
): ViewModel() {
    private val activity = fetcher.getActivity(activityId)

    internal fun getActivity(): Flow<Loadable<ContextualActivity>> {
        return activity
    }

    internal fun setObserving(isObserving: Boolean, onDone: () -> Unit = { }) {
        viewModelScope.launch {
            session.getUser().first()?.let {
                activity.first().ifLoaded {
                    activityRegistry.observer.toggle(it, this, isObserving, observation)
                    onDone()
                }
            }
        }
    }

    internal fun fetch() {
        viewModelScope.launch {
            fetcher.fetch()
        }
    }

    companion object {
        fun createFactory(
            session: Session,
            activityRegistry: ActivityRegistry,
            observation: Observation,
            fetcher: ContextualActivitiesFetcher,
            activityId: String
        ): ViewModelProvider.Factory {
            return viewModelFactory {
                addInitializer(ActivityDetailsViewModel::class) {
                    ActivityDetailsViewModel(
                        session,
                        activityRegistry,
                        observation,
                        fetcher,
                        activityId
                    )
                }
            }
        }
    }
}