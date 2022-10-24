package com.jeanbarrossilva.ongoing.platform.designsystem.extensions

import android.app.Activity
import android.os.Parcelable

inline fun <reified T> Activity.argumentOf(key: String): Lazy<T> {
    return lazy {
        intent?.extras?.get(key) as T
    }
}