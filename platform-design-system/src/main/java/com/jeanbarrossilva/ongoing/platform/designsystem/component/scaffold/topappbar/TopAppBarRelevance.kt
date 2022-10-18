package com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.topappbar

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle

sealed class TopAppBarRelevance {
    @get:Composable
    internal abstract val textStyle: TextStyle

    object Main: TopAppBarRelevance() {
        override val textStyle
            @Composable get() = MaterialTheme.typography.headlineSmall
    }

    data class Subsequent(val onNavigationRequest: () -> Unit): TopAppBarRelevance() {
        override val textStyle
            @Composable get() = MaterialTheme.typography.titleSmall
    }
}