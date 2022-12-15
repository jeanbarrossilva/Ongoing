package com.jeanbarrossilva.ongoing.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jeanbarrossilva.ongoing.core.session.Session
import kotlinx.coroutines.launch

class SettingsViewModel private constructor(private val session: Session): ViewModel() {
    fun logOut() {
        viewModelScope.launch {
            session.logOut()
        }
    }

    companion object {
        fun createFactory(session: Session): ViewModelProvider.Factory {
            return viewModelFactory {
                addInitializer(SettingsViewModel::class) {
                    SettingsViewModel(session)
                }
            }
        }
    }
}