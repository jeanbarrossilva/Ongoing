package com.jeanbarrossilva.ongoing.app

import android.app.Application
import com.jeanbarrossilva.ongoing.app.module.boundaryModule
import com.jeanbarrossilva.ongoing.app.module.coreModule
import com.jeanbarrossilva.ongoing.app.module.feature.contextualRegistryModule
import com.jeanbarrossilva.ongoing.app.module.feature.extensionsModule
import com.jeanbarrossilva.ongoing.app.module.feature.settingsModule
import com.jeanbarrossilva.ongoing.feature.activities.activitiesModule
import com.jeanbarrossilva.ongoing.feature.activitydetails.activityDetailsModule
import com.jeanbarrossilva.ongoing.feature.authentication.authenticationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

open class OngoingApplication: Application() {
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
            modules(authenticationModule, activitiesModule, activityDetailsModule, settingsModule)
            modules(extensionsModule, contextualRegistryModule)
        }
    }
}