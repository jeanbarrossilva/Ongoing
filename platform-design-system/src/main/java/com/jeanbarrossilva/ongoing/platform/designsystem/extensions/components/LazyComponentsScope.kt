package com.jeanbarrossilva.ongoing.platform.designsystem.extensions.components

import androidx.compose.runtime.Composable

/** Scope that allows the [addition][add] of [Composable]s. **/
class LazyComponentsScope internal constructor() {
    /** [MutableList] with the components that have been added. **/
    internal var components = mutableListOf<@Composable () -> Unit>()
        private set

    /**
     * Adds the given [component] to [components].
     *
     * @param component [Composable] to be added.
     **/
    fun add(component: @Composable () -> Unit) {
        components.add(component)
    }
}