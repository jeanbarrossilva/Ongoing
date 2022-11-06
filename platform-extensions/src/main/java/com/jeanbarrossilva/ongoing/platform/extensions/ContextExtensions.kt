package com.jeanbarrossilva.ongoing.platform.extensions

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.jeanbarrossilva.ongoing.platform.extensions.identification.CurrentVersionCodeProvider
import com.jeanbarrossilva.ongoing.platform.extensions.internal.get
import kotlinx.coroutines.flow.first

@PublishedApi
internal val Context.dataStore by preferencesDataStore("preferences")

suspend inline fun Context.onFirstRun(operation: () -> Unit) {
    val lastVersionCodePreference = intPreferencesKey("last_version_code")
    val lastVersionCode = dataStore.data.first()[lastVersionCodePreference]
    val isFirstRun = lastVersionCode == null
    if (isFirstRun) {
        operation()
        dataStore.edit {
            it[lastVersionCodePreference] = get<CurrentVersionCodeProvider>().provide()
        }
    }
}