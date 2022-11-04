package com.jeanbarrossilva.ongoing.feature.activityediting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ActivityEditingViewModel internal constructor(
    private val activityRegistry: ActivityRegistry,
    internal val mode: ActivityEditingMode
): ViewModel() {
    private val initialProps = when (mode) {
        is ActivityEditingMode.Addition -> ActivityEditingProps.empty
        is ActivityEditingMode.Modification -> ActivityEditingProps(mode.activity)
    }

    internal val props = MutableStateFlow(initialProps)

    internal fun updateProps(update: ActivityEditingProps.() -> ActivityEditingProps) {
        props.value = props.value.update()
    }

    internal fun onPropsChange(operation: suspend (props: ActivityEditingProps) -> Unit) {
        viewModelScope.launch {
            props.collect(operation)
        }
    }

    internal fun save() {
        viewModelScope.launch {
            mode.save(activityRegistry, props.value)
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