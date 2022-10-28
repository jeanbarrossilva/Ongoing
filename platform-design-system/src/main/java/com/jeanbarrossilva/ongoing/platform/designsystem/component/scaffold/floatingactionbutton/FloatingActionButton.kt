package com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.floatingactionbutton

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults.elevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.floatingactionbutton.FloatingActionButton.defaultContainerColor
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.floatingactionbutton.FloatingActionButton.defaultContentColor
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.floatingactionbutton.FloatingActionButton as _FloatingActionButton

internal object FloatingActionButton {
    val defaultContainerColor
        @Composable get() = MaterialTheme.colorScheme.primary
    val defaultContentColor
        @Composable get() = contentColorFor(defaultContainerColor)
}

@Composable
fun FloatingActionButton(
    enableability: FloatingActionButtonEnableability,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val availability = FloatingActionButtonAvailability.Available(enableability)
    _FloatingActionButton(onClick, modifier, availability, content)
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    availability: FloatingActionButtonAvailability = FloatingActionButtonAvailability.Available(),
    content: @Composable () -> Unit
) {
    val enableability = when (availability) {
        is FloatingActionButtonAvailability.Available -> availability.enableability
        is FloatingActionButtonAvailability.Unavailable -> null
    }
    val containerColor by
        animateColorAsState(enableability?.containerColor ?: defaultContainerColor)
    val contentColor by animateColorAsState(enableability?.contentColor ?: defaultContentColor)

    AnimatedVisibility(
        visible = availability is FloatingActionButtonAvailability.Available,
        modifier,
        enter = fadeIn() + scaleIn(),
        exit = fadeOut() + scaleOut()
    ) {
        FloatingActionButton(
            onClick = {
                if (enableability is FloatingActionButtonEnableability.Enabled) {
                    onClick()
                }
            },
            containerColor = containerColor,
            contentColor = contentColor,
            elevation = elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp,
                focusedElevation = 0.dp
            ),
            content = content
        )
    }
}

@Composable
private fun FloatingActionButton(isEnabled: Boolean) {
    _FloatingActionButton(FloatingActionButtonEnableability of isEnabled, onClick = { }) {
        Icon(Icons.Rounded.Add, contentDescription = "Add")
    }
}

@Composable
@Preview
private fun EnabledFloatingActionButtonPreview() {
    OngoingTheme {
        _FloatingActionButton(isEnabled = true)
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun DisabledFloatingActionButtonPreview() {
    OngoingTheme {
        _FloatingActionButton(isEnabled = false)
    }
}