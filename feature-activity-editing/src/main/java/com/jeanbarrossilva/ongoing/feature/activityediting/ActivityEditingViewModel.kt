package com.jeanbarrossilva.ongoing.feature.activityediting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jeanbarrossilva.ongoing.feature.activityediting.domain.EditingActivity
import com.jeanbarrossilva.ongoing.feature.activityediting.domain.EditingStatus
import com.jeanbarrossilva.ongoing.feature.activityediting.infra.ActivityEditingGateway
import com.jeanbarrossilva.ongoing.platform.loadable.Loadable
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.emptySerializableList
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.flowOf
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.ifLoaded
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.innerMap
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.loadableFlowOf
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.unwrap
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ActivityEditingViewModel internal constructor(private val gateway: ActivityEditingGateway):
    ViewModel() {
    private val activity = loadableFlowOf { gateway.getActivity() }

    internal val isChanged =
        flowOf(false) { activity.innerMap { gateway.isChanged(it).unwrap().first() } }
    internal val isValid =
        flowOf(false) { activity.innerMap { gateway.isValid(it).unwrap().first() } }
    internal val availableStatuses =
        loadableFlowOf(emptySerializableList(), gateway::getAvailableStatuses).asStateFlow()

    internal fun getActivity(): StateFlow<Loadable<EditingActivity>> {
        return activity.asStateFlow()
    }

    internal fun setName(name: String) {
        updateActivity {
            copy(name = name)
        }
    }

    internal fun setStatus(status: EditingStatus) {
        updateActivity {
            copy(status = status)
        }
    }

    internal fun edit() {
        viewModelScope.launch {
            activity.unwrap().first().let {
                viewModelScope.launch {
                    gateway.edit(it)
                }
            }
        }
    }

    private fun updateActivity(update: EditingActivity.() -> EditingActivity) {
        activity.apply {
            value.ifLoaded {
                value = Loadable.Loaded(update())
            }
        }
    }

    companion object {
        internal fun createFactory(gateway: ActivityEditingGateway): ViewModelProvider.Factory {
            return viewModelFactory {
                addInitializer(ActivityEditingViewModel::class) {
                    ActivityEditingViewModel(gateway)
                }
            }
        }
    }
}