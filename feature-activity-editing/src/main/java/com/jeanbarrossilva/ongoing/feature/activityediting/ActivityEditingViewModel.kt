package com.jeanbarrossilva.ongoing.feature.activityediting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualActivity
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.session.user.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

internal class ActivityEditingViewModel private constructor(
    private val user: User,
    private val activityRegistry: ActivityRegistry
): ViewModel() {
    val activity = MutableStateFlow<ContextualActivity?>(null)

    fun save() {
        activity.value?.toActivity()?.let {
            viewModelScope.launch {
                activityRegistry.setName(it.id, it.name)
                activityRegistry.setCurrentStatus(it.id, it.currentStatus)
            }
        }
    }

    companion object {
        fun getFactory(user: User, activityRegistry: ActivityRegistry): ViewModelProvider.Factory {
            return viewModelFactory {
                addInitializer(ActivityEditingViewModel::class) {
                    ActivityEditingViewModel(user, activityRegistry)
                }
            }
        }
    }
}