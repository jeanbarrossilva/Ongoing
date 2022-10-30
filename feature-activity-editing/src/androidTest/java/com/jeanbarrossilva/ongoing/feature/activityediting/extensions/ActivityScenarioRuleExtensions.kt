package com.jeanbarrossilva.ongoing.feature.activityediting.extensions

import androidx.activity.ComponentActivity
import androidx.test.ext.junit.rules.ActivityScenarioRule

internal val <T: ComponentActivity> ActivityScenarioRule<T>.activity: T
    get() {
        var activity: T? = null
        scenario.onActivity { activity = it }
        return activity ?: throw IllegalStateException(
            "Activity was not set in the ActivityScenarioRule!"
        )
    }