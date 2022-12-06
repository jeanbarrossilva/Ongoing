package com.jeanbarrossilva.ongoing.feature.activities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.context.registry.extensions.toContextualActivity
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.user.UserRepository
import com.jeanbarrossilva.ongoing.platform.loadable.Loadable
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.loadableFlowOf
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.toLoadable
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.toSerializableList
import com.jeanbarrossilva.ongoing.platform.loadable.type.SerializableList
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class ActivitiesViewModel private constructor(
    private val session: Session,
    private val userRepository: UserRepository,
    private val activityRegistry: ActivityRegistry
): ViewModel() {
    private val activities = loadableFlowOf<SerializableList<ContextualActivity>>()

    internal val user = flow { emitAll(session.getUser()) }

    init {
        fetch()
    }

    fun getActivities(): StateFlow<Loadable<SerializableList<ContextualActivity>>> {
        return activities.asStateFlow()
    }

    private fun fetch() {
        viewModelScope.launch {
            activities.value = activityRegistry
                .getActivities()
                .map { it.toContextualActivity(session, userRepository) }
                .toSerializableList()
                .toLoadable()
        }
    }

    companion object {
        fun createFactory(
            session: Session,
            userRepository: UserRepository,
            activityRegistry: ActivityRegistry
        ): ViewModelProvider.Factory {
            return viewModelFactory {
                addInitializer(ActivitiesViewModel::class) {
                    ActivitiesViewModel(session, userRepository, activityRegistry)
                }
            }
        }
    }
}