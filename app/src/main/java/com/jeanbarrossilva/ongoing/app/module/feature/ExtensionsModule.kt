package com.jeanbarrossilva.ongoing.app.module.feature

import com.jeanbarrossilva.ongoing.app.BuildConfig
import com.jeanbarrossilva.ongoing.platform.extensions.identification.CurrentVersionCodeProvider
import org.koin.dsl.module

internal val extensionsModule = module {
    single {
        CurrentVersionCodeProvider(BuildConfig::VERSION_CODE)
    }
}