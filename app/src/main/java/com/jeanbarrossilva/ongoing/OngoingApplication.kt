package com.jeanbarrossilva.ongoing

import android.app.Application
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
            modules(ongoingModule)
        }
    }
}