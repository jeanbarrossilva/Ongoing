package com.jeanbarrossilva.ongoing.app

import android.app.Application
import com.jeanbarrossilva.ongoing.app.module.boundaryModule
import com.jeanbarrossilva.ongoing.app.module.coreModule
import com.jeanbarrossilva.ongoing.app.module.feature.authenticationModule
import com.jeanbarrossilva.ongoing.app.module.feature.extensionsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

internal open class OngoingApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        setUpInjection()
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }

    private fun setUpInjection() {
        startKoin {
            androidContext(this@OngoingApplication)
            modules(coreModule, boundaryModule)
            modules(authenticationModule, extensionsModule)
        }
    }
}