package com.jeanbarrossilva.ongoing.feature.activitydetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jeanbarrossilva.ongoing.feature.activitydetails.infra.ActivityDetailsGateway
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class ActivityDetailsViewModel internal constructor(private val gateway: ActivityDetailsGateway):
    ViewModel() {
    internal val activity = flow { emit(gateway.getActivity()) }

    internal fun fetch() {
        viewModelScope.launch {
            gateway.fetch()
        }
    }

    internal fun setObserving(isObserving: Boolean) {
        viewModelScope.launch {
            gateway.setObserving(isObserving)
        }
    }

    companion object {
        internal fun createFactory(gateway: ActivityDetailsGateway): ViewModelProvider.Factory {
            return viewModelFactory {
                addInitializer(ActivityDetailsViewModel::class) {
                    ActivityDetailsViewModel(gateway)
                }
            }
        }
    }
}