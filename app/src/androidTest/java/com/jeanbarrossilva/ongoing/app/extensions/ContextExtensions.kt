package com.jeanbarrossilva.ongoing.app.extensions

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import kotlin.reflect.KClass

/** [KClass] of the [Activity] that's the launcher one. **/
@Suppress("DEPRECATION", "UNCHECKED_CAST")
internal val Context.launcherActivityClass: KClass<Activity>?
    get() = packageManager?.let {
        val intent = it.getLaunchIntentForPackage(packageName) ?: return null
        val flags = PackageManager.GET_ACTIVITIES
        val className = it.queryIntentActivities(intent, flags).first().activityInfo.name
        return Class.forName(className).kotlin as KClass<Activity>
    }