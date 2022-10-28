package com.jeanbarrossilva.ongoing.platform.designsystem.component.input.textfield

import com.jeanbarrossilva.ongoing.platform.designsystem.extensions.isValid
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
sealed class TextFieldEnableability {
    internal fun isEnabled(): Boolean {
        contract { returns(true) implies (this@TextFieldEnableability is Enabled) }
        return this is Enabled
    }

    internal fun hasErrors(value: String): Boolean {
        contract { returns(true) implies (this@TextFieldEnableability is Enabled) }
        return this is Enabled && !rules.isValid(value)
    }

    data class Enabled(
        internal val isReadOnly: Boolean = false,
        internal val rules: List<TextFieldRule> = emptyList()
    ): TextFieldEnableability()

    object Disabled: TextFieldEnableability()

    companion object {
        infix fun of(isEnabled: Boolean): TextFieldEnableability {
            return if (isEnabled) Enabled() else Disabled
        }
    }
}