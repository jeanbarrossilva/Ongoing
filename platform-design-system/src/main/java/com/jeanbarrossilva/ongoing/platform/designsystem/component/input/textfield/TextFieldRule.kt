package com.jeanbarrossilva.ongoing.platform.designsystem.component.input.textfield

data class TextFieldRule(
    internal val message: String,
    internal val isValid: (value: String) -> Boolean
)
