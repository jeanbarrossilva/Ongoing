package com.jeanbarrossilva.ongoing.platform.registry.extensions

import com.jeanbarrossilva.ongoing.platform.registry.OngoingDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.scope.Scope

val Scope.database
    get() = OngoingDatabase.getInstance(androidContext())