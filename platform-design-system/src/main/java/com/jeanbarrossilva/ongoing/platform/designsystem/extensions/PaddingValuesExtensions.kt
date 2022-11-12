package com.jeanbarrossilva.ongoing.platform.designsystem.extensions

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLayoutDirection

internal val PaddingValues.end
    @Composable get() = calculateEndPadding(LocalLayoutDirection.current)
internal val PaddingValues.start
    @Composable get() = calculateStartPadding(LocalLayoutDirection.current)

@Composable
operator fun PaddingValues.plus(other: PaddingValues): PaddingValues {
    return PaddingValues(
        start + other.start,
        calculateTopPadding() + other.calculateTopPadding(),
        end + other.end,
        calculateBottomPadding() + other.calculateBottomPadding()
    )
}