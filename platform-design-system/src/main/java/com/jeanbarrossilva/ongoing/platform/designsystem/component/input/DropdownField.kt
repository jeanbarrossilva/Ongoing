package com.jeanbarrossilva.ongoing.platform.designsystem.component.input

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpSize
import com.jeanbarrossilva.ongoing.platform.designsystem.R
import com.jeanbarrossilva.ongoing.platform.designsystem.component.input.textfield.TextField
import com.jeanbarrossilva.ongoing.platform.designsystem.component.input.textfield.TextFieldEnableability
import com.jeanbarrossilva.ongoing.platform.designsystem.component.input.textfield.TextFieldRule
import com.jeanbarrossilva.ongoing.platform.designsystem.component.input.textfield.submitter.TextFieldSubmitter
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.Size
import com.jeanbarrossilva.ongoing.platform.designsystem.extensions.rememberTextFieldSubmitter
import com.jeanbarrossilva.ongoing.platform.designsystem.extensions.toDpOffset
import com.jeanbarrossilva.ongoing.platform.designsystem.extensions.toDpSize
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

@Composable
fun DropdownField(
    isExpanded: Boolean,
    onExpansionToggle: (isExpanded: Boolean) -> Unit,
    value: String,
    label: (@Composable () -> Unit),
    modifier: Modifier = Modifier,
    rules: List<TextFieldRule> = emptyList(),
    submitter: TextFieldSubmitter = rememberTextFieldSubmitter(),
    content: @Composable ColumnScope.(width: Dp) -> Unit
) {
    val density = LocalDensity.current
    var offset by remember { mutableStateOf(DpOffset.Zero) }
    var size by remember { mutableStateOf(DpSize.Zero) }
    val indicatorRotationDegrees by animateFloatAsState(if (isExpanded) -180f else 0f)

    Box(modifier) {
        TextField(
            value,
            onValueChange = { _, _ -> },
            label = label,
            Modifier
                .clickable { onExpansionToggle(true) }
                .fillMaxWidth(),
            TextFieldEnableability.Enabled(isReadOnly = true, rules, submitter),
            trailingIcon = {
                Icon(
                    Icons.Rounded.ArrowDropDown,
                    contentDescription = stringResource(
                        R.string.platform_design_system_dropdown_field_indicator_content_description
                    ),
                    Modifier.rotate(indicatorRotationDegrees)
                )
            },
            onPlacement = {
                offset = it.positionInParent().toDpOffset(density)
                size = it.size.toDpSize(density)
            }
        )

        DropdownMenu(
            isExpanded,
            onDismissRequest = { onExpansionToggle(false) },
            offset = with(offset) { copy(y = y + Size.Spacing.xxs) },
            content = { content(size.width) }
        )
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun DropdownFieldPreview() {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    OngoingTheme {
        DropdownField(
            isExpanded,
            onExpansionToggle = { isExpanded = it },
            "Value",
            label = { Text("Label") }
        ) { width ->
            0.until(6).forEach { index ->
                DropdownMenuItem(
                    text = { Text("Item $index") },
                    onClick = { isExpanded = false },
                    Modifier.width(width)
                )
            }
        }
    }
}