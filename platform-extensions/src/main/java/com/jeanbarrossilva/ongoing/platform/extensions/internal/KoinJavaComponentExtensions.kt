package com.jeanbarrossilva.ongoing.platform.extensions.internal

import org.koin.java.KoinJavaComponent

@PublishedApi
internal inline fun <reified T> get(): T {
    return KoinJavaComponent.get(T::class.java)
}