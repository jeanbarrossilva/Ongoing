package com.jeanbarrossilva.ongoing.app

import android.app.Application
import com.jeanbarrossilva.ongoing.app.module.boundaryModule
import com.jeanbarrossilva.ongoing.app.module.coreModule
import com.jeanbarrossilva.ongoing.core.session.SessionManager
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

internal open class OngoingApplication: Application() {
    private val sessionManager by inject<SessionManager>()

    override fun onCreate() {
        super.onCreate()
        setUpInjection()
        setUpSession()
    }

    private fun setUpInjection() {
        startKoin {
            androidContext(this@OngoingApplication)
            modules(coreModule, boundaryModule)
        }
    }

    private fun setUpSession() {
        MainScope().launch {
            sessionManager.authenticate()
        }
    }
}