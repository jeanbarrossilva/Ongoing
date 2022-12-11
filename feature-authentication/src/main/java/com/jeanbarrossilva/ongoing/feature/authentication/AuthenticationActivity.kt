package com.jeanbarrossilva.ongoing.feature.authentication

import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.platform.designsystem.core.composable.ComposableActivity
import org.koin.android.ext.android.inject

class AuthenticationActivity internal constructor(): ComposableActivity() {
    private val session by inject<Session>()
    private val viewModel by viewModels<AuthenticationViewModel> {
        AuthenticationViewModel.createFactory(session)
    }

    @Composable
    override fun Content() {
        Authentication(viewModel, onNavigationRequest = onBackPressedDispatcher::onBackPressed)
    }
}