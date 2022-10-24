package com.jeanbarrossilva.ongoing.platform.designsystem.extensions

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Shape
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.PlaceholderSize

fun Modifier.placeholder(size: PlaceholderSize, isVisible: Boolean): Modifier {
    return composed {
        placeholder(size, MaterialTheme.shapes.small, isVisible)
    }
}

fun Modifier.placeholder(size: PlaceholderSize, shape: Shape, isVisible: Boolean):
    Modifier {
    return composed {
        placeholder(
            isVisible,
            MaterialTheme.colorScheme.surfaceVariant,
            shape,
            PlaceholderHighlight.shimmer()
        )
            .`if`(isVisible) { then(size.modifier) }
    }
}

@Suppress("UnnecessaryComposedModifier")
internal inline fun Modifier.`if`(
    crossinline condition: @Composable () -> Boolean,
    crossinline update: @Composable Modifier.() -> Modifier
): Modifier {
    return composed {
        `if`(condition()) {
            update()
        }
    }
}