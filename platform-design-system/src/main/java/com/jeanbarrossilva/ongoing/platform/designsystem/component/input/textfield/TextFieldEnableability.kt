package com.jeanbarrossilva.ongoing.platform.designsystem.component.input.textfield

import com.jeanbarrossilva.ongoing.platform.designsystem.component.input.textfield.submitter.TextFieldSubmitter
import com.jeanbarrossilva.ongoing.platform.designsystem.extensions.isValid
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
sealed class TextFieldEnableability {
    internal fun isEnabled(): Boolean {
        contract { returns(true) implies (this@TextFieldEnableability is Enabled) }
        return this is Enabled
    }

    internal fun isValid(value: String): Boolean {
        return this.isEnabled() && rules.isValid(value)
    }

    data class Enabled(
        internal val isReadOnly: Boolean = false,
        internal val rules: List<TextFieldRule> = emptyList(),
        internal val submitter: TextFieldSubmitter = TextFieldSubmitter()
    ): TextFieldEnableability()

    object Disabled: TextFieldEnableability()

    companion object {
        infix fun of(isEnabled: Boolean): TextFieldEnableability {
            return if (isEnabled) Enabled() else Disabled
        }
    }
}