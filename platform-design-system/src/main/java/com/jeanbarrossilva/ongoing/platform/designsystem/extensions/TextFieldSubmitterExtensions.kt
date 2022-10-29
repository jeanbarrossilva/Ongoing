package com.jeanbarrossilva.ongoing.platform.designsystem.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.jeanbarrossilva.ongoing.platform.designsystem.component.input.textfield.submitter.TextFieldSubmitter

@Composable
fun rememberTextFieldSubmitter(): TextFieldSubmitter {
    return remember {
        TextFieldSubmitter()
    }
}