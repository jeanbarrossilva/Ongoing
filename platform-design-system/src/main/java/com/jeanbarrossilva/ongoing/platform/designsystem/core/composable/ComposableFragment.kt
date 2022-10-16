package com.jeanbarrossilva.ongoing.platform.designsystem.core.composable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

abstract class ComposableFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return context?.let {
            ComposeView(it).apply {
                setContent {
                    ThemedContent()
                }
            }
        }
    }

    @Composable
    protected abstract fun Content()

    @Composable
    private fun ThemedContent() {
        OngoingTheme {
            Content()
        }
    }
}