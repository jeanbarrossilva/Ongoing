package com.jeanbarrossilva.ongoing.app.extensions

import org.koin.java.KoinJavaComponent

/** Gets the injected [T] value. **/
internal inline fun <reified T> get(): T {
    return KoinJavaComponent.get(T::class.java)
}