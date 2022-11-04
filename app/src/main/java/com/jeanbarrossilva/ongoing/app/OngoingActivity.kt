package com.jeanbarrossilva.ongoing.app

import androidx.compose.runtime.Composable
import com.jeanbarrossilva.ongoing.platform.designsystem.core.composable.ComposableActivity

internal class OngoingActivity: ComposableActivity() {
    @Composable
    override fun Content() {
        Ongoing()
    }
}