package com.jeanbarrossilva.ongoing.platform.designsystem.configuration

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.isSpecified
import com.jeanbarrossilva.ongoing.platform.designsystem.extensions.`if`

abstract class PlaceholderSize protected constructor() {
    protected abstract val width: Dp

    @get:Composable
    protected abstract val height: Dp

    internal val modifier = Modifier
        .`if`({ width.isSpecified }) { width(width) }
        .`if`({ height.isSpecified }) { height(height) }

    data class Text(
        override val width: Dp,
        private val textStyle: @Composable () -> TextStyle = { LocalTextStyle.current }
    ) : PlaceholderSize() {
        private val density
            @Composable get() = LocalDensity.current

        override val height
            @Composable get() = with(density) { textStyle().fontSize.toDp() }
    }

    companion object {
        infix fun of(value: Dp): PlaceholderSize {
            return object: PlaceholderSize() {
                override val width = value

                override val height
                    @Composable get() = value
            }
        }
    }
}