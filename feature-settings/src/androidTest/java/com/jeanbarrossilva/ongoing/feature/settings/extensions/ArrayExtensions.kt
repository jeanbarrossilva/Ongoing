package com.jeanbarrossilva.ongoing.feature.settings.extensions

/**
 * Creates an [Array] with the non-`null` [elements].
 *
 * @param elements Elements to be in the [Array].
 **/
@Suppress("UNCHECKED_CAST")
internal inline fun <reified T> arrayOfNotNull(vararg elements: T?): Array<T> {
    return elements.filter { it != null }.toTypedArray() as Array<T>
}