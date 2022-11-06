package com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.topappbar

sealed interface TopAppBarStyle {
    object Root: TopAppBarStyle

    fun interface Navigable: TopAppBarStyle {
        fun onNavigationRequest()
    }
}