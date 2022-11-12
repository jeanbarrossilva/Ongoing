package com.jeanbarrossilva.ongoing.feature.activitydetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jeanbarrossilva.ongoing.context.registry.extensions.toContextualActivity
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.session.user.UserRepository
import com.jeanbarrossilva.ongoing.feature.activitydetails.extensions.orEmpty
import com.jeanbarrossilva.ongoing.platform.loadable.Loadable
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.loadableFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class ActivityDetailsViewModel private constructor(
    private val userRepository: UserRepository,
    private val activityRegistry: ActivityRegistry,
    private val activityId: String?
): ViewModel() {
    internal val activity = loadableFlow {
        activityId
            ?.let { activityRegistry.getActivityById(it) }
            .orEmpty
            .filterNotNull()
            .map { it.toContextualActivity(userRepository) }
            .map { Loadable.Loaded(it) }
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