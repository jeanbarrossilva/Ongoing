package com.jeanbarrossilva.ongoing.feature.activitydetails.extensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal val <T> Flow<T>?.orEmpty
    get() = this ?: flow { }