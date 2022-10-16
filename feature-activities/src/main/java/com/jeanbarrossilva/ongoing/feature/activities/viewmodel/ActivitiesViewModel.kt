package com.jeanbarrossilva.ongoing.feature.activities.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.session.user.User
import com.jeanbarrossilva.ongoing.core.session.user.UserRepository
import com.jeanbarrossilva.ongoing.feature.activities.context.ContextualActivity
import com.jeanbarrossilva.ongoing.feature.activities.extensions.mapToContextualActivity
import com.jeanbarrossilva.ongoing.feature.activities.extensions.toContextualActivity
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

internal class ActivitiesViewModel(
    private val userRepository: UserRepository,
    private val activityRegistry: ActivityRegistry
): ViewModel() {
    val activities = flow {
        emitAll(activityRegistry.getActivities().map { it.mapToContextualActivity(userRepository) })
    }

    init {
        populateWithSamples()
    }

    private fun populateWithSamples() {
        viewModelScope.launch {
            val isNotPopulated = activities.first().isEmpty()
            if (isNotPopulated) {
                ContextualActivity.samples.forEach { activity ->
                    activityRegistry.register(ownerUserId = User.sample.id, activity.name)
                }
            }
        }
    }
}