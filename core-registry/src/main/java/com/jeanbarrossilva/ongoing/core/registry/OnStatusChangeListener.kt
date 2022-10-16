package com.jeanbarrossilva.ongoing.core.registry

import com.jeanbarrossilva.ongoing.core.registry.activity.Activity

fun interface OnStatusChangeListener {
    fun onStatusChange(activity: Activity)
}