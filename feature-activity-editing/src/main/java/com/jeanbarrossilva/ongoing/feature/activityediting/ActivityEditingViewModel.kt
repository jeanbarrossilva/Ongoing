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
    private val initialProps = when (mode) {
        is ActivityEditingMode.Addition -> ActivityEditingProps.empty
        is ActivityEditingMode.Modification -> ActivityEditingProps(mode.activity)
    }

    val props = MutableStateFlow(initialProps)

    fun updateProps(update: ActivityEditingProps.() -> ActivityEditingProps) {
        props.value = props.value.update()
    }

    fun onPropsChange(operation: suspend (props: ActivityEditingProps) -> Unit) {
        viewModelScope.launch {
            props.collect(operation)
        }
    }

    fun save() {
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