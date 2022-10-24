package com.jeanbarrossilva.ongoing.platform.designsystem.extensions

import androidx.compose.ui.unit.Dp

internal val Dp?.orUnspecified
    get() = this ?: Dp.Unspecified