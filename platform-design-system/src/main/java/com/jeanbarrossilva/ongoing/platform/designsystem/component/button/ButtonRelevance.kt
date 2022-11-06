package com.jeanbarrossilva.ongoing.platform.designsystem.component.button

import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

sealed class ButtonRelevance {
    protected abstract val state: ButtonPriority

    @get:Composable
    internal abstract val containerColor: Color

    @get:Composable
    internal abstract val contentColor: Color

    data class Primary(override val state: ButtonPriority = ButtonPriority.DEFAULT): ButtonRelevance() {
        override val containerColor
            @Composable get() = state.color
        override val contentColor
            @Composable get() = contentColorFor(containerColor)
    }

    data class Secondary(override val state: ButtonPriority = ButtonPriority.DEFAULT): ButtonRelevance() {
        override val containerColor
            @Composable get() = Color.Transparent
        override val contentColor
            @Composable get() = state.color
    }
}