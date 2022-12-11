package com.jeanbarrossilva.ongoing.extensions

import androidx.activity.OnBackPressedCallback
import androidx.compose.ui.test.junit4.AndroidComposeTestRule

/** Dispatches all [AndroidComposeTestRule.activity]'s [OnBackPressedCallback]s. **/
internal fun AndroidComposeTestRule<*, *>.pressBack() {
    activity.onBackPressedDispatcher.onBackPressed()
}