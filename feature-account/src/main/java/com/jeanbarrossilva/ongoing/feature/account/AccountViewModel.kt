package com.jeanbarrossilva.ongoing.feature.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jeanbarrossilva.ongoing.core.session.SessionManager
import kotlinx.coroutines.launch

class AccountViewModel private constructor(private val sessionManager: SessionManager):
    ViewModel() {
    fun logOut() {
        viewModelScope.launch {
            sessionManager.logOut()
        }
    }

    companion object {
        fun createFactory(sessionManager: SessionManager): ViewModelProvider.Factory {
            return viewModelFactory {
                addInitializer(AccountViewModel::class) {
                    AccountViewModel(sessionManager)
                }
            }
        }
    }
}