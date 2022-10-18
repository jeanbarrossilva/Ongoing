package com.jeanbarrossilva.ongoing.app

import android.app.Application
import com.jeanbarrossilva.ongoing.app.module.boundaryModule
import com.jeanbarrossilva.ongoing.app.module.coreModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

internal class OngoingApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        setUpInjection()
    }

    private fun setUpInjection() {
        startKoin {
            androidContext(this@OngoingApplication)
            modules(coreModule, boundaryModule)
        }
    }
}