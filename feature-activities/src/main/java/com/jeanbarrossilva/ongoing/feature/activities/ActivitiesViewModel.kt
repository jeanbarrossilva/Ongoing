package com.jeanbarrossilva.ongoing.feature.activities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class ActivitiesViewModel internal constructor(private val gateway: ActivitiesGateway):
    ViewModel() {
    internal val owner = flow { emitAll(gateway.getCurrentOwner()) }
    internal val activities = flow { emitAll(gateway.getActivities()) }
    internal val selection = MutableStateFlow<ActivitiesSelection>(ActivitiesSelection.Off)

    internal fun fetch() {
        viewModelScope.launch {
            gateway.fetch()
        }
    }

    internal fun unregister(activities: List<ContextualActivity>) {
        val activityIds = activities.map(ContextualActivity::id)
        viewModelScope.launch { gateway.unregister(activityIds) }
        selection.value = ActivitiesSelection.Off
    }

    companion object {
        fun createFactory(gateway: ActivitiesGateway): ViewModelProvider.Factory {
            return viewModelFactory {
                addInitializer(ActivitiesViewModel::class) {
                    ActivitiesViewModel(gateway)
                }
            }
        }
    }
}