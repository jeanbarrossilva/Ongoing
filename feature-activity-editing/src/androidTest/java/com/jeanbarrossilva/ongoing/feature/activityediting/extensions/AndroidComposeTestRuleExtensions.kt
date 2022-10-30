package com.jeanbarrossilva.ongoing.feature.activityediting.extensions

import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.core.os.bundleOf
import androidx.test.ext.junit.rules.ActivityScenarioRule

internal inline fun <reified T: ComponentActivity> createAndroidComposeRule(
    context: Context,
    vararg args: Pair<String, Any?>,
    setup: () -> Unit = { }
): AndroidComposeTestRule<ActivityScenarioRule<T>, T> {
    val extras = bundleOf(*args)
    val intent = Intent(context, T::class.java).apply { putExtras(extras) }
    val activityScenarioRule = ActivityScenarioRule<T>(intent)
    setup()
    return AndroidComposeTestRule(activityScenarioRule, ActivityScenarioRule<T>::activity)
}