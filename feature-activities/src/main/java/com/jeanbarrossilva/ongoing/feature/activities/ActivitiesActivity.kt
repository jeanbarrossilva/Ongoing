package com.jeanbarrossilva.ongoing.feature.activities

import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import com.jeanbarrossilva.ongoing.platform.designsystem.core.composable.ComposableActivity
import org.koin.android.ext.android.inject

class ActivitiesActivity internal constructor(): ComposableActivity() {
    private val gateway by inject<ActivitiesGateway>()
    private val boundary by inject<ActivitiesBoundary>()
    private val viewModel by viewModels<ActivitiesViewModel> {
        ActivitiesViewModel.createFactory(gateway)
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetch()
    }

    @Composable
    override fun Content() {
        Activities(viewModel, boundary)
    }
}