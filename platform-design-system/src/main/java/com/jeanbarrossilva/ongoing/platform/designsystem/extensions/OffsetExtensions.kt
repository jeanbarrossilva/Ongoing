package com.jeanbarrossilva.ongoing.platform.designsystem.extensions

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpOffset

internal fun Offset.toDpOffset(density: Density): DpOffset {
    val (xInDp, yInDp) = with(density) { x.toDp() to y.toDp() }
    return DpOffset(xInDp, yInDp)
}