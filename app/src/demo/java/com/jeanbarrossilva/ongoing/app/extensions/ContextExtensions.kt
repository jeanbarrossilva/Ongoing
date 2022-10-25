package com.jeanbarrossilva.ongoing.app.extensions

import android.content.Context
import androidx.core.content.edit
import com.jeanbarrossilva.ongoing.BuildConfig

private const val LAST_VERSION_CODE_KEY = "last_version_code"
private const val NONEXISTENT_LAST_VERSION_CODE = -1

internal val Context.preferences
    get() = getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)

internal fun Context.onFirstRun(operation: () -> Unit) {
    val lastVersionCode = preferences.getInt(LAST_VERSION_CODE_KEY, NONEXISTENT_LAST_VERSION_CODE)
    val isFirstRun = lastVersionCode == NONEXISTENT_LAST_VERSION_CODE
    if (isFirstRun) {
        operation()
        preferences.edit {
            putInt(LAST_VERSION_CODE_KEY, BuildConfig.VERSION_CODE)
        }
    }
}