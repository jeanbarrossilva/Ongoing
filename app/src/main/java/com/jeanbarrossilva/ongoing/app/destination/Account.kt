package com.jeanbarrossilva.ongoing.app.destination

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jeanbarrossilva.ongoing.core.session.SessionManager
import com.jeanbarrossilva.ongoing.core.session.user.User
import com.jeanbarrossilva.ongoing.feature.account.Account
import com.jeanbarrossilva.ongoing.feature.account.AccountViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.get

@Destination
@Composable
internal fun Account(navigator: DestinationsNavigator, user: User, modifier: Modifier = Modifier) {
    val sessionManager = get<SessionManager>()
    val viewModelFactory = AccountViewModel.createFactory(sessionManager)
    val viewModel = viewModel<AccountViewModel>(factory = viewModelFactory)
    Account(user, viewModel, onNavigationRequest = navigator::popBackStack, modifier)
}