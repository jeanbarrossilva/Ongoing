package com.jeanbarrossilva.ongoing.app

import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.platform.extensions.onFirstRun
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

internal class DemoOngoingApplication: OngoingApplication() {
    private val activityRegistry by inject<ActivityRegistry>()

    override fun onCreate() {
        super.onCreate()
        registerSampleActivitiesOnFirstRun()
    }

    private fun registerSampleActivitiesOnFirstRun() {
        MainScope().launch {
            onFirstRun {
                registerSampleActivities()
            }
        }
    }

    private suspend fun registerSampleActivities() {
        withEachActivity {
            activityRegistry.register(ownerUserId, name, statuses)
        }
    }

    private inline fun withEachActivity(operation: Activity.() -> Unit) {
        ContextualActivity.samples.map(ContextualActivity::toActivity).forEach {
            operation(it)
        }
    }
}