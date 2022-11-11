package com.jeanbarrossilva.ongoing.feature.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jeanbarrossilva.ongoing.core.session.Session
import kotlinx.coroutines.launch

class AuthenticationViewModel internal constructor(private val session: Session): ViewModel() {
    internal fun logIn() {
        viewModelScope.launch {
            session.logIn()
        }
    }

    companion object {
        fun createFactory(session: Session): ViewModelProvider.Factory {
            return viewModelFactory {
                addInitializer(AuthenticationViewModel::class) {
                    AuthenticationViewModel(session)
                }
            }
        }
    }
}