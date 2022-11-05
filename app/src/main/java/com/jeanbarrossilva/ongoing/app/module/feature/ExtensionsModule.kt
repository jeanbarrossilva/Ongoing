package com.jeanbarrossilva.ongoing.app.module.feature

import com.jeanbarrossilva.ongoing.BuildConfig
import com.jeanbarrossilva.ongoing.platform.extensions.identification.ApplicationIdProvider
import com.jeanbarrossilva.ongoing.platform.extensions.identification.CurrentVersionCodeProvider
import org.koin.dsl.module

internal val extensionsModule = module {
    single { ApplicationIdProvider(BuildConfig::APPLICATION_ID) }
    single { CurrentVersionCodeProvider(BuildConfig::VERSION_CODE) }
}