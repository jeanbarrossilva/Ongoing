package com.jeanbarrossilva.ongoing.feature.activities.component.activitycard

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal enum class ActivityCardProminence {
    STILL {
        override val elevation = 0.dp

        @Composable
        override fun getBorderStroke(isPressed: Boolean): BorderStroke {
            val width by animateDpAsState(if (isPressed) (-1).dp else DefaultUnit)
            return BorderStroke(width, MaterialTheme.colorScheme.secondaryContainer)
        }
    },
    HOVERED {
        override val elevation
            get() = DefaultUnit

        @Composable
        override fun getBorderStroke(isPressed: Boolean): BorderStroke? {
            return null
        }
    };

    abstract val elevation: Dp

    @Composable
    abstract fun getBorderStroke(isPressed: Boolean): BorderStroke?

    companion object {
        private val DefaultUnit = 2.dp
    }
}