package com.jeanbarrossilva.ongoing.app.extensions

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule

/** Creates an [AndroidComposeTestRule] with the launcher [ComponentActivity].**/
@Suppress("UNCHECKED_CAST")
internal fun createAndroidComposeRule():
    AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity> {
    val context = ApplicationProvider.getApplicationContext<Context>()
    return createAndroidComposeRule(context.launcherActivityClass?.java as Class<ComponentActivity>)
}