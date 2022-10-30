package com.jeanbarrossilva.ongoing.feature.activityediting.extensions

import androidx.activity.OnBackPressedCallback

internal fun onBackPressed(isEnabled: Boolean = true, operation: () -> Unit):
    OnBackPressedCallback {
    return object: OnBackPressedCallback(isEnabled) {
        override fun handleOnBackPressed() {
            operation()
        }
    }
}