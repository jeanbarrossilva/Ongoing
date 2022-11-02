package com.jeanbarrossilva.ongoing.feature.activitydetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jeanbarrossilva.ongoing.context.registry.extensions.toContextualActivity
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.session.user.UserRepository
import com.jeanbarrossilva.ongoing.feature.activitydetails.extensions.orEmpty
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class ActivityDetailsViewModel private constructor(
    private val userRepository: UserRepository,
    private val activityRegistry: ActivityRegistry,
    private val activityId: String?
): ViewModel() {
    internal val activity = flow {
        activityId
            ?.let { activityRegistry.getActivityById(it) }
            .orEmpty
            .map { it?.toContextualActivity(userRepository) }
            .collect(::emit)
    }

    companion object {
        fun createFactory(
            userRepository: UserRepository,
            activityRegistry: ActivityRegistry,
            activityId: String?
        ): ViewModelProvider.Factory {
            return viewModelFactory {
                addInitializer(ActivityDetailsViewModel::class) {
                    ActivityDetailsViewModel(userRepository, activityRegistry, activityId)
                }
            }
        }
    }
}