package com.jeanbarrossilva.ongoing.app

import com.jeanbarrossilva.ongoing.app.extensions.onFirstRun
import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualActivity
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.session.SessionManager
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

internal class DemoOngoingApplication: OngoingApplication() {
    private val sessionManager by inject<SessionManager>()
    private val activityRegistry by inject<ActivityRegistry>()

    override fun onCreate() {
        super.onCreate()
        registerSampleActivitiesOnFirstRun()
    }

    private fun registerSampleActivitiesOnFirstRun() {
        onFirstRun {
            registerSampleActivities()
        }
    }

    private fun registerSampleActivities() {
        MainScope().launch {
            withUserId userId@{
                withEachActivity {
                    activityRegistry.register(this@userId, name, statuses)
                }
            }
        }
    }

    private suspend inline fun withUserId(operation: String.() -> Unit) {
        sessionManager.authenticate()
        sessionManager.getUser().first()?.id?.let {
            operation(it)
        }
    }

    private inline fun withEachActivity(operation: Activity.() -> Unit) {
        ContextualActivity.samples.map(ContextualActivity::toActivity).forEach {
            operation(it)
        }
    }
}