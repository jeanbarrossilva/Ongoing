package com.jeanbarrossilva.ongoing.feature.activities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.fetcher.ContextualActivitiesFetcher
import com.jeanbarrossilva.ongoing.context.registry.extensions.getActivities
import com.jeanbarrossilva.ongoing.context.registry.extensions.unregister
import com.jeanbarrossilva.ongoing.core.session.Session
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class ActivitiesViewModel private constructor(
    internal val session: Session,
    internal val fetcher: ContextualActivitiesFetcher
): ViewModel() {
    internal val user = flow { emitAll(session.getUser()) }
    internal val activities = fetcher.getActivities()
    internal val selection = MutableStateFlow<ActivitiesSelection>(ActivitiesSelection.Off)

    internal fun unregister(activities: List<ContextualActivity>) {
        viewModelScope.launch {
            activities.forEach {
                fetcher.unregister(it.id)
            }
        }
        selection.value = ActivitiesSelection.Off
    }

    companion object {
        fun createFactory(session: Session, fetcher: ContextualActivitiesFetcher):
            ViewModelProvider.Factory {
            return viewModelFactory {
                addInitializer(ActivitiesViewModel::class) {
                    ActivitiesViewModel(session, fetcher)
                }
            }
        }
    }
}