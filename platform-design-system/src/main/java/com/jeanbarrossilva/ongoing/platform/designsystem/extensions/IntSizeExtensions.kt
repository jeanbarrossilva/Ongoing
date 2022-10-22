package com.jeanbarrossilva.ongoing.platform.designsystem.extensions

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntSize

internal fun IntSize.toDpSize(density: Density): DpSize {
    val (widthInDp, heightInDp) = with(density) { width.toDp() to height.toDp() }
    return DpSize(widthInDp, heightInDp)
}