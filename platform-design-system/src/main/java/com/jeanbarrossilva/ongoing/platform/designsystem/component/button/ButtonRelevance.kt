package com.jeanbarrossilva.ongoing.platform.designsystem.component.button

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

enum class ButtonRelevance {
    PRIMARY {
        override val containerColor
            @Composable get() = MaterialTheme.colorScheme.primary
        override val contentColor
            @Composable get() = MaterialTheme.colorScheme.onPrimary
    },
    SECONDARY {
        override val containerColor
            @Composable get() = Color.Transparent
        override val contentColor
            @Composable get() = MaterialTheme.colorScheme.primary
    };

    @get:Composable
    internal abstract val containerColor: Color

    @get:Composable
    internal abstract val contentColor: Color
}