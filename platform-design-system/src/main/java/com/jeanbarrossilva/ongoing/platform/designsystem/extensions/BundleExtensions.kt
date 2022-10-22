package com.jeanbarrossilva.ongoing.platform.designsystem.extensions

import android.os.Build
import android.os.Bundle
import android.os.Parcelable

@Suppress("FunctionName", "Deprecation")
@PublishedApi
internal inline fun <reified T : Parcelable> Bundle._getParcelable(key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelable(key, T::class.java)
    } else {
        getParcelable(key)
    }
}