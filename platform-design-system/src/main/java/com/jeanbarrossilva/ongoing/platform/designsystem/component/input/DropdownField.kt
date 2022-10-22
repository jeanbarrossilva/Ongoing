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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpSize
import com.jeanbarrossilva.ongoing.platform.designsystem.extensions.toDpOffset
import com.jeanbarrossilva.ongoing.platform.designsystem.extensions.toDpSize
import com.jeanbarrossilva.ongoing.platform.designsystem.R
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.Size
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownField(
    isExpanded: Boolean,
    onExpansionToggle: (isExpanded: Boolean) -> Unit,
    value: String,
    label: (@Composable () -> Unit),
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.(width: Dp) -> Unit
) {
    val density = LocalDensity.current
    var coordinates by remember { mutableStateOf(DpOffset.Zero) }
    var size by remember { mutableStateOf(DpSize.Zero) }
    val indicatorRotationDegrees by animateFloatAsState(if (isExpanded) -180f else 0f)

    Box(modifier) {
        TextField(
            value,
            onValueChange = { },
            Modifier
                .onGloballyPositioned {
                    coordinates = it.positionInParent().toDpOffset(density)
                    size = it.size.toDpSize(density)
                }
                .clickable { onExpansionToggle(true) }
                .fillMaxWidth(),
            enabled = false,
            readOnly = true,
            label = label,
            trailingIcon = {
                Icon(
                    Icons.Rounded.ArrowDropDown,
                    contentDescription = stringResource(
                        R.string.platform_design_system_dropdown_field_indicator_content_description
                    ),
                    Modifier.rotate(indicatorRotationDegrees)
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                disabledIndicatorColor = Color.Transparent,
                disabledTrailingIconColor = MaterialTheme.colorScheme.onSurface,
                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )

        DropdownMenu(
            isExpanded,
            onDismissRequest = { onExpansionToggle(false) },
            offset = with(coordinates) { copy(y = y + Size.Spacing.xxs) },
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