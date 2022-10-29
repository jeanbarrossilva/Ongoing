package com.jeanbarrossilva.ongoing.platform.designsystem.component.input.textfield

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.jeanbarrossilva.ongoing.platform.designsystem.component.input.textfield.colors.TextFieldColors
import com.jeanbarrossilva.ongoing.platform.designsystem.component.input.textfield.submitter.OnSubmissionListener
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.Size
import com.jeanbarrossilva.ongoing.platform.designsystem.extensions.message
import com.jeanbarrossilva.ongoing.platform.designsystem.extensions.textFieldColors
import com.jeanbarrossilva.ongoing.platform.designsystem.extensions.toDpSize
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme
import com.jeanbarrossilva.ongoing.platform.designsystem.component.input.textfield.TextField as _TextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextField(
    value: String,
    onValueChange: (value: String, isValid: Boolean) -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enableability: TextFieldEnableability,
    trailingIcon: (@Composable () -> Unit)? = null,
    colors: TextFieldColors = textFieldColors(),
    onPlacement: (coordinates: LayoutCoordinates) -> Unit = { }
) {
    val density = LocalDensity.current
    val spacing = Size.Spacing.xs
    var coordinates by remember { mutableStateOf<LayoutCoordinates?>(null) }
    var errorMessageHeight by remember { mutableStateOf(Dp.Unspecified) }
    var shouldShowErrors by remember { mutableStateOf(false) }
    val onSubmissionListener = remember {
        OnSubmissionListener {
            shouldShowErrors = true
        }
    }

    DisposableEffect(coordinates) {
        coordinates?.let(onPlacement)
        onDispose { }
    }

    if (enableability.isEnabled()) {
        DisposableEffect(Unit) {
            enableability.submitter.addListener(onSubmissionListener)
            onDispose { enableability.submitter.removeListener(onSubmissionListener) }
        }
    }

    Column(verticalArrangement = Arrangement.spacedBy(spacing)) {
        TextField(
            value,
            onValueChange = { onValueChange(it, enableability.isValid(it)) },
            modifier.onGloballyPositioned { coordinates = it },
            enabled = enableability.isEnabled() && !enableability.isReadOnly,
            readOnly = enableability.isEnabled() && enableability.isReadOnly,
            label = label,
            trailingIcon = trailingIcon,
            isError = shouldShowErrors,
            colors = colors.toMaterialTextFieldColors(enableability)
        )

        if (enableability.isEnabled() && shouldShowErrors) {
            Text(
                enableability.rules.message,
                Modifier.onGloballyPositioned {
                    errorMessageHeight = it.size.toDpSize(density).height
                },
                MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
private fun TextField(isEnabled: Boolean, modifier: Modifier = Modifier) {
    _TextField(
        "Jean",
        onValueChange = { _, _ -> },
        label = { Text("Name") },
        modifier,
        TextFieldEnableability of isEnabled
    )
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun EnabledTextFieldPreview() {
    OngoingTheme {
        _TextField(isEnabled = true)
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun DisabledTextFieldPreview() {
    OngoingTheme {
        _TextField(isEnabled = false)
    }
}