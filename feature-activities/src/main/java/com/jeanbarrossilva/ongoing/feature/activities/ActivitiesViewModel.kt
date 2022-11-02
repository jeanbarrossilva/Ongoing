package com.jeanbarrossilva.ongoing.feature.activities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualActivity
import com.jeanbarrossilva.ongoing.context.registry.extensions.mapToContextualActivity
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.session.user.User
import com.jeanbarrossilva.ongoing.core.session.user.UserRepository
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ActivitiesViewModel private constructor(
    private val userRepository: UserRepository,
    private val activityRegistry: ActivityRegistry
): ViewModel() {
    internal val activities = flow {
        emitAll(activityRegistry.getActivities().map { it.mapToContextualActivity(userRepository) })
    }

    companion object {
        fun createFactory(userRepository: UserRepository, activityRegistry: ActivityRegistry):
            ViewModelProvider.Factory {
            return viewModelFactory {
                addInitializer(ActivitiesViewModel::class) {
                    ActivitiesViewModel(userRepository, activityRegistry)
                }
            }
        }
    }
}