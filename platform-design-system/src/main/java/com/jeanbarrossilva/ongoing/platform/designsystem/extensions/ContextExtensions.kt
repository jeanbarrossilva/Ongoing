package com.jeanbarrossilva.ongoing.platform.designsystem.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.core.os.bundleOf
import java.io.Serializable

inline fun <reified T: Activity> Context.startActivity(vararg args: Pair<String, Any?>) {
    val extras = bundleOf(*args)
    val intent = Intent(this, T::class.java).apply { putExtras(extras) }
    startActivity(intent)
}