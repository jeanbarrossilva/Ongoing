package com.jeanbarrossilva.ongoing.feature.activities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.fetcher.ContextualActivitiesFetcher
import com.jeanbarrossilva.ongoing.context.registry.extensions.getActivities
import com.jeanbarrossilva.ongoing.context.registry.extensions.unregister
import com.jeanbarrossilva.ongoing.context.user.extensions.toContextualUser
import com.jeanbarrossilva.ongoing.core.session.SessionManager
import com.jeanbarrossilva.ongoing.core.user.UserRepository
import com.jeanbarrossilva.ongoing.feature.activities.extensions.getUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ActivitiesViewModel internal constructor(
    internal val sessionManager: SessionManager,
    userRepository: UserRepository,
    internal val fetcher: ContextualActivitiesFetcher
): ViewModel() {
    internal val user = sessionManager.getUser(userRepository).map { it?.toContextualUser() }
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
        fun createFactory(
            sessionManager: SessionManager,
            userRepository: UserRepository,
            fetcher: ContextualActivitiesFetcher
        ): ViewModelProvider.Factory {
            return viewModelFactory {
                addInitializer(ActivitiesViewModel::class) {
                    ActivitiesViewModel(sessionManager, userRepository, fetcher)
                }
            }
        }
    }
}