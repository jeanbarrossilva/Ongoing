package com.jeanbarrossilva.ongoing.platform.designsystem.extensions

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.ScaffoldPadding
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.floatingactionbutton.FloatingActionButton

@Suppress("ComposableNaming")
@Composable
internal fun ScaffoldPadding(bars: PaddingValues, isFabVisible: Boolean): ScaffoldPadding {
    val fab = 0.dp
        .`if`(isFabVisible) { FloatingActionButton.Size + 16.dp * 2 }
        .let { PaddingValues(bottom = it) }
    return ScaffoldPadding(bars, fab)
}

@Suppress("ComposableNaming")
@Composable
internal fun ScaffoldPadding(bars: PaddingValues, fab: PaddingValues): ScaffoldPadding {
    val total = bars + fab
    val start = total.start
    val end = total.end
    return object: ScaffoldPadding() {
        override val bars = bars
        override val fab = fab

        override fun calculateBottomPadding(): Dp {
            return total.calculateBottomPadding()
        }

        override fun calculateLeftPadding(layoutDirection: LayoutDirection): Dp {
            return start
        }

        override fun calculateRightPadding(layoutDirection: LayoutDirection): Dp {
            return end
        }

        override fun calculateTopPadding(): Dp {
            return total.calculateTopPadding()
        }
    }
}