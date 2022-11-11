package com.jeanbarrossilva.ongoing.app.destination

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.feature.authentication.Authentication
import com.jeanbarrossilva.ongoing.feature.authentication.AuthenticationViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.get

@Composable
@Destination
internal fun Authentication(navigator: DestinationsNavigator, modifier: Modifier = Modifier) {
    val session = get<Session>()
    val viewModelFactory = AuthenticationViewModel.createFactory(session)
    val viewModel = viewModel<AuthenticationViewModel>(factory = viewModelFactory)
    Authentication(viewModel, onNavigationRequest = navigator::popBackStack, modifier)
}