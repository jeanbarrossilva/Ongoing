package com.jeanbarrossilva.ongoing.feature.activityediting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualActivity
import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

internal class ActivityEditingViewModel private constructor(
    private val activityRecorder: Activity.Recorder
): ViewModel() {
    val activity = MutableStateFlow<ContextualActivity?>(null)

    fun save() {
        activity.value?.toActivity()?.let {
            viewModelScope.launch {
                activityRecorder.name(it.id, it.name)
                activityRecorder.currentStatus(it.id, it.currentStatus)
            }
        }
    }

    companion object {
        fun createFactory(activityRecorder: Activity.Recorder): ViewModelProvider.Factory {
            return viewModelFactory {
                addInitializer(ActivityEditingViewModel::class) {
                    ActivityEditingViewModel(activityRecorder)
                }
            }
        }
    }
}