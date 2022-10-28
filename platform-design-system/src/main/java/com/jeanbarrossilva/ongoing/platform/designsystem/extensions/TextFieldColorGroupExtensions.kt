package com.jeanbarrossilva.ongoing.platform.designsystem.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.jeanbarrossilva.ongoing.platform.designsystem.component.input.textfield.colors.TextFieldColorGroup

@Composable
internal fun textFieldColorGroup(all: Color): TextFieldColorGroup {
    return textFieldColorGroup(text = all, indicator = all, trailingIcon = all, label = all)
}

@Composable
internal fun textFieldColorGroup(
    text: Color = TextFieldColorGroup.highlightColor,
    indicator: Color = TextFieldColorGroup.defaultColor,
    trailingIcon: Color = TextFieldColorGroup.defaultColor,
    label: Color = TextFieldColorGroup.defaultColor
): TextFieldColorGroup {
    return TextFieldColorGroup(text, indicator, trailingIcon, label)
}