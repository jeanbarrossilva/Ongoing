package com.jeanbarrossilva.ongoing.feature.activityediting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

internal class ActivityEditingViewModel private constructor(
    private val activityRegistry: ActivityRegistry,
    private val mode: ActivityEditingMode
): ViewModel() {
    private val initialActivity = when (mode) {
        is ActivityEditingMode.Addition -> null
        is ActivityEditingMode.Modification -> mode.activity
    }

    val name = MutableStateFlow(initialActivity?.name.orEmpty())
    val currentStatus = MutableStateFlow(initialActivity?.currentStatus)

    fun save() {
        viewModelScope.launch {
            mode.save(activityRegistry, name.value, requireNotNull(currentStatus.value))
        }
    }

    companion object {
        fun createFactory(activityRegistry: ActivityRegistry, mode: ActivityEditingMode):
            ViewModelProvider.Factory {
            return viewModelFactory {
                addInitializer(ActivityEditingViewModel::class) {
                    ActivityEditingViewModel(activityRegistry, mode)
                }
            }
        }
    }
}