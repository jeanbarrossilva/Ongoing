package com.jeanbarrossilva.ongoing.platform.designsystem.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.jeanbarrossilva.ongoing.platform.designsystem.component.input.textfield.colors.TextFieldColorGroup
import com.jeanbarrossilva.ongoing.platform.designsystem.component.input.textfield.colors.TextFieldColors
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.ContentAlpha

@Composable
fun textFieldColors(
    default: TextFieldColorGroup = textFieldColorGroup(),
    disabled: TextFieldColorGroup =
        textFieldColorGroup(TextFieldColorGroup.highlightColor.copy(ContentAlpha.DISABLED)),
    readOnly: TextFieldColorGroup = textFieldColorGroup(
        indicator = Color.Transparent,
        label = TextFieldColorGroup.defaultColor
    )
): TextFieldColors {
    return TextFieldColors(default, disabled, readOnly)
}