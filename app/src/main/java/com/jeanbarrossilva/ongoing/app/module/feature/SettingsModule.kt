package com.jeanbarrossilva.ongoing.app.module.feature

import com.jeanbarrossilva.ongoing.app.BuildConfig
import com.jeanbarrossilva.ongoing.app.R
import com.jeanbarrossilva.ongoing.feature.settings.app.AppNameProvider
import com.jeanbarrossilva.ongoing.feature.settings.app.CurrentVersionNameProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal val settingsModule = module {
    single {
        AppNameProvider {
            androidContext().getString(R.string.app_name)
        }
    }
    single { CurrentVersionNameProvider(BuildConfig::VERSION_NAME) }
}