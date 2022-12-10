package com.jeanbarrossilva.ongoing.platform.loadable

import android.os.Parcelable
import java.io.Serializable
import kotlinx.parcelize.Parcelize

sealed interface Loadable<T: Serializable?>: Parcelable {
    @Parcelize
    data class Loaded<T: Serializable?>(val value: T): Loadable<T>

    @Parcelize
    class Loading<T: Serializable?>: Loadable<T> {
        override fun toString(): String {
            return "Loadable.Loading"
        }
    }
}