package com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.floatingactionbutton

sealed interface FloatingActionButtonAvailability {
    data class Available(
        internal val enableability: FloatingActionButtonEnableability =
            FloatingActionButtonEnableability.Enabled
    ): FloatingActionButtonAvailability

    object Unavailable: FloatingActionButtonAvailability

    companion object {
        infix fun `for`(isAvailable: Boolean): FloatingActionButtonAvailability {
            return if (isAvailable) Available() else Unavailable
        }
    }
}