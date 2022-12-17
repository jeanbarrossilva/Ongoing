package com.jeanbarrossilva.ongoing.feature.settings.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.feature.settings.R
import com.jeanbarrossilva.ongoing.feature.settings.app.AppNameProvider
import com.jeanbarrossilva.ongoing.feature.settings.app.CurrentVersionNameProvider
import com.jeanbarrossilva.ongoing.platform.designsystem.component.background.Background
import com.jeanbarrossilva.ongoing.platform.designsystem.component.background.BackgroundContentSizing
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.ContentAlpha
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.Size
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

internal const val ABOUT_APP_NAME_TAG = "about_app_name"
internal const val ABOUT_CURRENT_VERSION_TAG = "about_current_version"

@Composable
internal fun About(appName: String, currentVersionName: String, modifier: Modifier = Modifier) {
    val contentAlpha = ContentAlpha.DISABLED

    Column(
        modifier.fillMaxWidth(),
        Arrangement.spacedBy(Size.Spacing.xxs),
        Alignment.CenterHorizontally
    ) {
        Text(
            appName,
            Modifier
                .alpha(contentAlpha)
                .testTag(ABOUT_APP_NAME_TAG),
            fontWeight = FontWeight.Bold
        )

        Text(
            stringResource(R.string.feature_settings_about_version, currentVersionName),
            Modifier
                .alpha(contentAlpha)
                .testTag(ABOUT_CURRENT_VERSION_TAG)
        )
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun AboutPreview() {
    OngoingTheme {
        Background(contentSizing = BackgroundContentSizing.WRAP) {
            About(AppNameProvider.sample.provide(), CurrentVersionNameProvider.sample.provide())
        }
    }
}