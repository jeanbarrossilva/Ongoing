package com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.floatingactionbutton

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.ContentAlpha

sealed class FloatingActionButtonEnableability {
    internal open val containerColor: Color
        @Composable get() = FloatingActionButton.defaultContainerColor
    internal open val contentColor
        @Composable get() = contentColorFor(containerColor)

    object Enabled: FloatingActionButtonEnableability()

    object Disabled: FloatingActionButtonEnableability() {
        override val containerColor
            @Composable get() = MaterialTheme.colorScheme.secondaryContainer
        override val contentColor
            @Composable get() = super.contentColor.copy(ContentAlpha.MEDIUM)
    }

    companion object {
        infix fun `for`(isEnabled: Boolean): FloatingActionButtonEnableability {
            return if (isEnabled) Enabled else Disabled
        }
    }
}