package com.jeanbarrossilva.ongoing.platform.designsystem.extensions

import com.jeanbarrossilva.ongoing.platform.designsystem.component.input.textfield.TextFieldRule

internal val Collection<TextFieldRule>.message
    get() = joinToString(separator = "\n") { it.message }.`if`({ size > 1 }) { prependIndent("- ") }

internal fun Collection<TextFieldRule>.isValid(value: String): Boolean {
    return all {
        it.isValid(value)
    }
}