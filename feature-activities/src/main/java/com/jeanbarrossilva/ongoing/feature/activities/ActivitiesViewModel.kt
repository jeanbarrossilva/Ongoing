package com.jeanbarrossilva.ongoing.feature.activities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.fetcher.ContextualActivitiesFetcher
import com.jeanbarrossilva.ongoing.context.registry.extensions.getActivities
import com.jeanbarrossilva.ongoing.core.session.Session
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class ActivitiesViewModel private constructor(
    private val session: Session,
    fetcher: ContextualActivitiesFetcher
): ViewModel() {
    internal val user = flow { emitAll(session.getUser()) }
    internal val activities = fetcher.getActivities()

    companion object {
        fun createFactory(session: Session, fetcher: ContextualActivitiesFetcher):
            ViewModelProvider.Factory {
            return viewModelFactory {
                addInitializer(ActivitiesViewModel::class) {
                    ActivitiesViewModel(session, fetcher)
                }
            }
        }
    }
}