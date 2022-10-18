package com.jeanbarrossilva.ongoing.platform.designsystem.core.composable

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import com.jeanbarrossilva.ongoing.platform.designsystem.databinding.ActivityComposableBinding
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

abstract class ComposableActivity: AppCompatActivity() {
    private var binding: ActivityComposableBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityComposableBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setUpContent()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    @Composable
    protected abstract fun Content()

    private fun setUpContent() {
        binding?.root?.setContent {
            ThemedContent()
        }
    }

    @Composable
    private fun ThemedContent() {
        OngoingTheme {
            Content()
        }
    }
}