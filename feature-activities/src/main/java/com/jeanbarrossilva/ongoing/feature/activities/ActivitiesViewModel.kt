package com.jeanbarrossilva.ongoing.feature.activities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jeanbarrossilva.ongoing.core.session.Session
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class ActivitiesViewModel private constructor(private val session: Session): ViewModel() {
    internal val user = flow {
        emitAll(session.getUser())
    }

    companion object {
        fun createFactory(session: Session): ViewModelProvider.Factory {
            return viewModelFactory {
                addInitializer(ActivitiesViewModel::class) {
                    ActivitiesViewModel(session)
                }
            }
        }
    }
}