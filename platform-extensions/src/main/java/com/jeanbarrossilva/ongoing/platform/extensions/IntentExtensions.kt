package com.jeanbarrossilva.ongoing.platform.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.core.os.bundleOf

inline fun <reified T: Activity> Intent(context: Context, vararg extras: Pair<String, Any?>):
    Intent {
    val extrasBundle = bundleOf(*extras)
    return Intent(context, T::class.java).apply { putExtras(extrasBundle) }
}