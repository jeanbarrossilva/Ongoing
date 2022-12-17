package com.jeanbarrossilva.ongoing.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.fetcher.ContextualActivitiesFetcher
import com.jeanbarrossilva.ongoing.context.registry.extensions.clear
import com.jeanbarrossilva.ongoing.context.registry.extensions.getActivities
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.user.User
import com.jeanbarrossilva.ongoing.feature.settings.app.AppNameProvider
import com.jeanbarrossilva.ongoing.feature.settings.app.CurrentVersionNameProvider
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.ifLoaded
import com.jeanbarrossilva.ongoing.platform.loadable.type.SerializableList
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SettingsViewModel internal constructor(
    private val session: Session,
    private val user: User,
    appNameProvider: AppNameProvider,
    currentVersionNameProvider: CurrentVersionNameProvider,
    private val activitiesFetcher: ContextualActivitiesFetcher
): ViewModel() {
    val appName = appNameProvider.provide()
    val currentVersionName = currentVersionNameProvider.provide()
    val hasActivities = activitiesFetcher
        .getActivities()
        .map { it.ifLoaded(SerializableList<ContextualActivity>::isNotEmpty) ?: false }

    init {
        fetchActivities()
    }

    fun logOut() {
        viewModelScope.launch {
            session.logOut()
        }
    }

    fun clearActivities() {
        viewModelScope.launch {
            activitiesFetcher.clear(user.id)
        }
    }

    private fun fetchActivities() {
        viewModelScope.launch {
            activitiesFetcher.fetch()
        }
    }

    companion object {
        fun createFactory(
            session: Session,
            user: User,
            appNameProvider: AppNameProvider,
            currentVersionNameProvider: CurrentVersionNameProvider,
            activitiesFetcher: ContextualActivitiesFetcher
        ): ViewModelProvider.Factory {
            return viewModelFactory {
                addInitializer(SettingsViewModel::class) {
                    SettingsViewModel(
                        session,
                        user,
                        appNameProvider,
                        currentVersionNameProvider,
                        activitiesFetcher
                    )
                }
            }
        }
    }
}