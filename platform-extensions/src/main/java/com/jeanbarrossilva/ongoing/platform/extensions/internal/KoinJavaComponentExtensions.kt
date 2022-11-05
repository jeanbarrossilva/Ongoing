package com.jeanbarrossilva.ongoing.platform.extensions.internal

import org.koin.java.KoinJavaComponent

@PublishedApi
internal inline fun <reified T> inject(): Lazy<T> {
    return KoinJavaComponent.inject(T::class.java)
}