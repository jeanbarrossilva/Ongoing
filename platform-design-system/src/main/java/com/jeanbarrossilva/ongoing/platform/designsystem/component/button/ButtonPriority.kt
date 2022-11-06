package com.jeanbarrossilva.ongoing.platform.designsystem.component.button

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

enum class ButtonPriority {
    DEFAULT {
        override val color
            @Composable get() = MaterialTheme.colorScheme.primary
    },
    IMPORTANT {
        override val color
            @Composable get() = MaterialTheme.colorScheme.error
    };

    @get:Composable
    internal abstract val color: Color
}