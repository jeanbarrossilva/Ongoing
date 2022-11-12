package com.jeanbarrossilva.ongoing.platform.loadable

import android.os.Parcelable
import java.io.Serializable
import kotlinx.parcelize.Parcelize

sealed interface Loadable<T>: Parcelable {
    @Parcelize
    data class Loaded<T: Serializable>(val value: T): Loadable<T>

    @Parcelize
    class Loading<T: Serializable>: Loadable<T>

    fun <R> ifLoaded(operation: T.() -> R): R? {
        return if (this is Loaded) value.operation() else null
    }
}