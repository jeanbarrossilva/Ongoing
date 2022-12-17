package com.jeanbarrossilva.ongoing.platform.designsystem.extensions

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.jeanbarrossilva.ongoing.platform.designsystem.extensions.components.LazyComponentsScope

/**
 * Lazily displays the [Composable]s added to the [LazyComponentsScope].
 *
 * @param build [LazyComponentsScope]-related operations.
 **/
fun LazyListScope.components(build: LazyComponentsScope.() -> Unit) {
    val components = LazyComponentsScope().apply(build).components.toList()
    items(components) { it() }
}