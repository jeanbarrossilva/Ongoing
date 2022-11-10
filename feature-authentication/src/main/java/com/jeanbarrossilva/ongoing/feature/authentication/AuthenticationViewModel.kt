package com.jeanbarrossilva.ongoing.feature.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jeanbarrossilva.ongoing.core.session.SessionManager
import kotlinx.coroutines.launch

class AuthenticationViewModel internal constructor(private val sessionManager: SessionManager):
    ViewModel() {
    internal fun logIn() {
        viewModelScope.launch {
            sessionManager.logIn()
        }
    }

    companion object {
        fun createFactory(sessionManager: SessionManager): ViewModelProvider.Factory {
            return viewModelFactory {
                addInitializer(AuthenticationViewModel::class) {
                    AuthenticationViewModel(sessionManager)
                }
            }
        }
    }
}