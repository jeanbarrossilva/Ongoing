package com.jeanbarrossilva.ongoing.context.registry.component.activityicon

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.Size

enum class ActivityIconSize {
    SMALL {
        override val value = 24.dp
        override val padding = Size.Spacing.s

        override val shape
            @Composable get() = MaterialTheme.shapes.small
    },
    LARGE {
        override val value = 64.dp
        override val padding = Size.Spacing.xxl

        override val shape
            @Composable get() = MaterialTheme.shapes.medium
    };

    protected abstract val value: Dp

    internal abstract val padding: Dp

    @get:Composable
    internal abstract val shape: Shape

    internal val adaptedValue
        get() = value - padding
}