package com.jeanbarrossilva.ongoing.feature.settings

import android.content.Context
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.core.app.ApplicationProvider
import com.jeanbarrossilva.ongoing.feature.settings.component.ABOUT_APP_NAME_TAG
import com.jeanbarrossilva.ongoing.feature.settings.component.ABOUT_CURRENT_VERSION_TAG
import com.jeanbarrossilva.ongoing.feature.settings.rule.SettingsTestRule
import com.jeanbarrossilva.ongoing.platform.registry.test.PlatformRegistryTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

internal class AboutTests {
    private val platformRegistryRule = PlatformRegistryTestRule.create()
    private val composeRule = createComposeRule()
    private val settingsRule = SettingsTestRule(platformRegistryRule, composeRule)

    private val context
        get() = ApplicationProvider.getApplicationContext<Context>()

    @get:Rule
    val ruleChain: RuleChain? = RuleChain
        .outerRule(platformRegistryRule)
        .around(composeRule)
        .around(settingsRule)

    @Test
    fun showAppName() {
        composeRule
            .onNodeWithTag(ABOUT_APP_NAME_TAG)
            .assertTextEquals(settingsRule.viewModel.appName)
    }

    @Test
    fun showCurrentVersion() {
        composeRule
            .onNodeWithTag(ABOUT_CURRENT_VERSION_TAG)
            .assertTextEquals(
                context.getString(
                    R.string.feature_settings_about_version,
                    settingsRule.viewModel.currentVersionName
                )
            )
    }
}