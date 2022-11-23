package com.jeanbarrossilva.ongoing.feature.activitydetails.extensions

internal inline fun <reified T> arrayOfNotNull(vararg elements: T): Array<T> {
    return arrayOf(*elements).filter { it == null }.toTypedArray()
}