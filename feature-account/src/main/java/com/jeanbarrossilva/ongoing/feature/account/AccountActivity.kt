package com.jeanbarrossilva.ongoing.feature.account

import android.content.Context
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.user.User
import com.jeanbarrossilva.ongoing.platform.designsystem.core.composable.ComposableActivity
import com.jeanbarrossilva.ongoing.platform.designsystem.extensions.argumentOf
import com.jeanbarrossilva.ongoing.platform.extensions.startActivity
import org.koin.android.ext.android.inject

class AccountActivity internal constructor(): ComposableActivity() {
    private val session by inject<Session>()
    private val user by argumentOf<User>(USER_KEY)
    private val viewModel by viewModels<AccountViewModel> { AccountViewModel.createFactory(session) }

    @Composable
    override fun Content() {
        Account(user, viewModel, onNavigationRequest = onBackPressedDispatcher::onBackPressed)
    }

    companion object {
        private const val USER_KEY = "user"

        fun start(context: Context, user: User) {
            context.startActivity<AccountActivity>(USER_KEY to user)
        }
    }
}