package com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.floatingactionbutton

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
sealed class FloatingActionButtonAvailability {
    internal fun isAvailable(): Boolean {
        contract { returns(true) implies (this@FloatingActionButtonAvailability is Available) }
        return this is Available
    }

    data class Available(
        internal val enableability: FloatingActionButtonEnableability =
            FloatingActionButtonEnableability.Enabled
    ): FloatingActionButtonAvailability()

    object Unavailable: FloatingActionButtonAvailability()

    companion object {
        infix fun `for`(isAvailable: Boolean): FloatingActionButtonAvailability {
            return if (isAvailable) Available() else Unavailable
        }
    }
}