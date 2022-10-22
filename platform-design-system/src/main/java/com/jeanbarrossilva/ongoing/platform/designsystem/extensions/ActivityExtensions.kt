package com.jeanbarrossilva.ongoing.platform.designsystem.extensions

import android.app.Activity
import android.os.Parcelable

inline fun <reified T: Parcelable> Activity.argumentOf(key: String): Lazy<T> {
    return lazy {
        intent?.extras?._getParcelable<T>(key)!!
    }
}