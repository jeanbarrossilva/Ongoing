package com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.floatingactionbutton

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.ContentAlpha
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

sealed class FloatingActionButtonEnableability {
    internal open val containerColor: Color
        @Composable get() = FloatingActionButton.defaultContainerColor
    internal open val contentColor
        @Composable get() = contentColorFor(containerColor)

    @OptIn(ExperimentalContracts::class)
    internal fun isEnabled(): Boolean {
        contract { returns(true) implies (this@FloatingActionButtonEnableability is Enabled) }
        return this is Enabled
    }

    object Enabled: FloatingActionButtonEnableability()

    data class Disabled(internal val isInteractive: Boolean = false):
        FloatingActionButtonEnableability() {
        override val containerColor
            @Composable get() = MaterialTheme.colorScheme.secondaryContainer
        override val contentColor
            @Composable get() = super.contentColor.copy(ContentAlpha.MEDIUM)
    }

    companion object {
        infix fun of(isEnabled: Boolean): FloatingActionButtonEnableability {
            return if (isEnabled) Enabled else Disabled()
        }
    }
}