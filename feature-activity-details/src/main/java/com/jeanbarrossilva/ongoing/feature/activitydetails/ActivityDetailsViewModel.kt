package com.jeanbarrossilva.ongoing.feature.activitydetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jeanbarrossilva.ongoing.context.registry.extensions.toContextualActivity
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.session.user.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

internal class ActivityDetailsViewModel private constructor(
    private val userRepository: UserRepository,
    private val activityRegistry: ActivityRegistry
): ViewModel() {
    private val activityId = MutableStateFlow<String?>(null)

    val activity = flow {
        activityId
            .map { it?.let { activityRegistry.getActivityById(it) } }
            .map { flow -> flow?.map { it?.toContextualActivity(userRepository) } }
            .collect { flow -> flow?.collect { emit(it) } }
    }

    fun setActivityId(id: String) {
        activityId.value = id
    }

    companion object {
        fun createFactory(userRepository: UserRepository, activityRegistry: ActivityRegistry):
            ViewModelProvider.Factory {
            return viewModelFactory {
                addInitializer(ActivityDetailsViewModel::class) {
                    ActivityDetailsViewModel(userRepository, activityRegistry)
                }
            }
        }
    }
}