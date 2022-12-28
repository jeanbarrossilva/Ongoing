package com.jeanbarrossilva.ongoing.feature.activityediting.extensions

import org.koin.java.KoinJavaComponent

/** Gets the injected [T] value lazily. **/
internal inline fun <reified T> inject(): Lazy<T> {
    return KoinJavaComponent.inject(T::class.java)
}