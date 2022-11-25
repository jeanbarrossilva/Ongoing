package com.jeanbarrossilva.ongoing.platform.extensions

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.jeanbarrossilva.ongoing.platform.extensions.identification.CurrentVersionCodeProvider
import com.jeanbarrossilva.ongoing.platform.extensions.internal.get
import com.jeanbarrossilva.ongoing.platform.extensions.internal.uuid
import kotlinx.coroutines.flow.first

@PublishedApi
internal val Context.dataStore by preferencesDataStore("preferences")

val Context.notificationManager
    get() = requireNotNull(getSystemService<NotificationManager>())

fun Context.createNotificationChannel(id: String, @StringRes nameRes: Int) {
    val name = getString(nameRes)
    val channel = NotificationChannel(id, name, NotificationManager.IMPORTANCE_DEFAULT)
    notificationManager.createNotificationChannel(channel)
}

@Suppress("UnnecessaryVariable")
fun Context.notify(
    channelId: String,
    @DrawableRes smallIconRes: Int,
    title: String,
    text: String,
    intent: Intent
) {
    val id = uuid().sumOf(Char::code)
    val requestCode = id
    val pendingIntent =
        PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_IMMUTABLE)
    val notification = NotificationCompat
        .Builder(this, channelId)
        .setSmallIcon(smallIconRes)
        .setContentTitle(title)
        .setContentText(text)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .build()
    notificationManager.notify(id, notification)
}

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

inline fun <reified T: Activity> Context.startActivity(vararg args: Pair<String, Any?>) {
    val intent = Intent<T>(this, *args)
    startActivity(intent)
}