package com.jeanbarrossilva.ongoing.platform.designsystem.extensions

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled

internal fun SemanticsNodeInteraction.assertIsEnabled(isEnabled: Boolean) {
    if (isEnabled) {
        assertIsEnabled()
    } else {
        assertIsNotEnabled()
    }
}