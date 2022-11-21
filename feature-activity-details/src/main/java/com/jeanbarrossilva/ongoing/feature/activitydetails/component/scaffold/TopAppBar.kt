package com.jeanbarrossilva.ongoing.feature.activitydetails.component.scaffold

import android.content.res.Configuration
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.feature.activitydetails.component.ActivityObservationIcon
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.topappbar.TopAppBar
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.topappbar.TopAppBarStyle
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme
import com.jeanbarrossilva.ongoing.platform.loadable.Loadable
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.ifLoaded
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.map

@Composable
internal fun TopAppBar(
    isObserving: Loadable<Boolean>,
    onActivityObservationToggle: (isObservingActivity: Boolean) -> Unit,
    onNavigationRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(TopAppBarStyle.Navigable(onNavigationRequest), modifier) {
        IconButton(
            onClick = { isObserving.map(Boolean::not).ifLoaded(onActivityObservationToggle) }
        ) {
            ActivityObservationIcon(isObserving)
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun TopAppBarPreview() {
    OngoingTheme {
        TopAppBar(
            isObserving = Loadable.Loaded(false),
            onActivityObservationToggle = { },
            onNavigationRequest = { }
        )
    }
}