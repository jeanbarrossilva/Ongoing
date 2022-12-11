package com.jeanbarrossilva.ongoing.extensions

import android.app.Activity
import androidx.test.core.app.ActivityScenario
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal suspend fun <T: Activity> ActivityScenario<T>.getActivity(): T {
    return suspendCoroutine { continuation ->
        onActivity {
            continuation.resume(it)
        }
    }
}