package com.jeanbarrossilva.ongoing.feature.activityediting.extensions

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule

internal fun <T: ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<T>, T>.pressBack() {
    with(activity) {
        runOnUiThread {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}