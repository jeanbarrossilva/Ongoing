package com.jeanbarrossilva.ongoing.platform.designsystem.component.input.textfield.colors

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldColors as MaterialTextFieldColors
import androidx.compose.material3.TextFieldDefaults.textFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.jeanbarrossilva.ongoing.platform.designsystem.component.input.textfield.TextFieldEnableability

data class TextFieldColors internal constructor(
    val default: TextFieldColorGroup,
    val disabled: TextFieldColorGroup,
    val readOnly: TextFieldColorGroup
) {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun toMaterialTextFieldColors(enableability: TextFieldEnableability):
        MaterialTextFieldColors {
        val isDisabled = enableability is TextFieldEnableability.Disabled
        val isReadOnly = enableability.isEnabled() && enableability.isReadOnly
        val disabledTextColor = when {
            isDisabled -> disabled.text
            isReadOnly -> readOnly.text
            else -> Color.Unspecified
        }
        val disabledIndicatorColor = when {
            isDisabled -> disabled.indicator
            isReadOnly -> readOnly.indicator
            else -> Color.Unspecified
        }
        val disabledTrailingIconColor = when {
            isDisabled -> disabled.trailingIcon
            isReadOnly -> readOnly.trailingIcon
            else -> default.trailingIcon
        }
        val disabledLabelColor = when {
            isDisabled -> disabled.label
            isReadOnly -> readOnly.label
            else -> Color.Unspecified
        }
        return textFieldColors(
            default.text,
            disabledTextColor,
            focusedIndicatorColor = default.indicator,
            disabledIndicatorColor = disabledIndicatorColor,
            focusedTrailingIconColor = default.trailingIcon,
            disabledTrailingIconColor = disabledTrailingIconColor,
            focusedLabelColor = default.label,
            disabledLabelColor = disabledLabelColor
        )
    }
}