package com.jeanbarrossilva.ongoing.platform.loadable

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

sealed interface Loadable<T: Serializable?>: Parcelable {
    @Parcelize
    class Loading<T: Serializable?>: Loadable<T> {
        override fun toString(): String {
            return "Loadable.Loading"
        }
    }

    @Parcelize
    data class Loaded<T: Serializable?>(val value: T): Loadable<T>

    @Parcelize
    data class Failed<T: Serializable?>(val error: Throwable): Loadable<T>
}