package com.jeanbarrossilva.ongoing.app.destination

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.feature.activityediting.ActivityEditing
import com.jeanbarrossilva.ongoing.feature.activityediting.ActivityEditingMode
import com.jeanbarrossilva.ongoing.feature.activityediting.ActivityEditingViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.get

@Composable
@Destination
internal fun ActivityEditing(
    navigator: DestinationsNavigator,
    mode: ActivityEditingMode,
    modifier: Modifier = Modifier
) {
    val activityRegistry = get<ActivityRegistry>()
    val viewModelFactory =
        remember { ActivityEditingViewModel.createFactory(activityRegistry, mode) }
    val viewModel = viewModel<ActivityEditingViewModel>(factory = viewModelFactory)

    ActivityEditing(
        viewModel,
        onNavigationRequest = navigator::popBackStack,
        onDone = navigator::popBackStack,
        modifier
    )
}