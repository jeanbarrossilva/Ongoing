package com.jeanbarrossilva.ongoing.feature.authentication

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cached
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.jeanbarrossilva.ongoing.feature.authentication.Authentication.TAG
import com.jeanbarrossilva.ongoing.feature.authentication.component.Buttons
import com.jeanbarrossilva.ongoing.platform.designsystem.component.background.Background
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.Size
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

object Authentication {
    const val TAG = "authentication"
}

@Composable
fun Authentication(
    viewModel: AuthenticationViewModel,
    onNavigationRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    Authentication(onLogInRequest = viewModel::logIn, onNavigationRequest, modifier)
}

@Composable
internal fun Authentication(
    onLogInRequest: () -> Unit,
    onNavigationRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    Background(
        modifier
            .padding(Size.Spacing.xxl)
            .testTag(TAG)
    ) {
        ConstraintLayout(Modifier.fillMaxSize()) {
            val (logoRef, buttonsRef) = createRefs()

            Icon(
                Icons.Rounded.Cached,
                contentDescription =
                    stringResource(R.string.feature_authentication_logo_content_description),
                Modifier
                    .constrainAs(logoRef) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(buttonsRef.top)
                    }
                    .size(124.dp),
                MaterialTheme.colorScheme.primary
            )

            Buttons(
                onLogInRequest = {
                    onLogInRequest()
                    onNavigationRequest()
                },
                onNavigationRequest,
                Modifier.constrainAs(buttonsRef) { bottom.linkTo(parent.bottom) }
            )
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun AuthenticationPreview() {
    OngoingTheme {
        Authentication(onLogInRequest = { }, onNavigationRequest = { })
    }
}