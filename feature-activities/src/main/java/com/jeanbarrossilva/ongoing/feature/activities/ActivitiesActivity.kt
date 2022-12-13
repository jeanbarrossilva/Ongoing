package com.jeanbarrossilva.ongoing.feature.activities

import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.fetcher.ContextualActivitiesFetcher
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.registry.observation.Observation
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.platform.designsystem.core.composable.ComposableActivity
import org.koin.android.ext.android.inject

class ActivitiesActivity internal constructor(): ComposableActivity() {
    private val session by inject<Session>()
    private val activityRegistry by inject<ActivityRegistry>()
    private val observation by inject<Observation>()
    private val fetcher by inject<ContextualActivitiesFetcher>()
    private val boundary by inject<ActivitiesBoundary>()
    private val viewModel by viewModels<ActivitiesViewModel> {
        ActivitiesViewModel.createFactory(session, fetcher)
    }

    @Composable
    override fun Content() {
        Activities(viewModel, boundary, activityRegistry, observation)
    }
}