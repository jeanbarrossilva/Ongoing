package com.jeanbarrossilva.ongoing.platform.designsystem.component.input.textfield.colors

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

data class TextFieldColorGroup(
    val text: Color,
    val indicator: Color,
    val trailingIcon: Color,
    val label: Color
) {
    companion object {
        internal val highlightColor
            @Composable get() = MaterialTheme.colorScheme.onSurface
        internal val defaultColor
            @Composable get() = MaterialTheme.colorScheme.onSurfaceVariant
    }
}