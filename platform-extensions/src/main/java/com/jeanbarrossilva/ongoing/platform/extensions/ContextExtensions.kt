package com.jeanbarrossilva.ongoing.platform.extensions

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.jeanbarrossilva.ongoing.platform.extensions.identification.ApplicationIdProvider
import com.jeanbarrossilva.ongoing.platform.extensions.identification.CurrentVersionCodeProvider
import com.jeanbarrossilva.ongoing.platform.extensions.internal.inject

@PublishedApi
internal const val LAST_VERSION_CODE_KEY = "last_version_code"

@PublishedApi
internal const val NONEXISTENT_LAST_VERSION_CODE = -1

@PublishedApi
internal val Context.preferences: SharedPreferences
    get() {
        val appIdProvider by inject<ApplicationIdProvider>()
        val appId = appIdProvider.provide()
        return getSharedPreferences(appId, Context.MODE_PRIVATE)
    }

inline fun Context.onFirstRun(operation: () -> Unit) {
    val currentVersionCodeProvider by inject<CurrentVersionCodeProvider>()
    val currentVersionCode = currentVersionCodeProvider.provide()
    val lastVersionCode = preferences.getInt(LAST_VERSION_CODE_KEY, NONEXISTENT_LAST_VERSION_CODE)
    val isFirstRun = lastVersionCode == NONEXISTENT_LAST_VERSION_CODE
    if (isFirstRun) {
        operation()
        preferences.edit {
            putInt(LAST_VERSION_CODE_KEY, currentVersionCode)
        }
    }
}