package com.jeanbarrossilva.ongoing.platform.registry.test.activity

import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.SessionManager
import com.jeanbarrossilva.ongoing.core.session.extensions.session
import com.jeanbarrossilva.ongoing.platform.registry.OngoingDatabase
import com.jeanbarrossilva.ongoing.platform.registry.extensions.getActivityRegistry
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.rules.ExternalResource

internal class ActivityRegistryRule(
    private val sessionManager: SessionManager,
    private val database: () -> OngoingDatabase
): ExternalResource() {
    private var activityRegistry: ActivityRegistry? = null

    override fun before() {
        activityRegistry = database().getActivityRegistry(sessionManager)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun after() {
        sessionManager.session<Session.SignedIn>()?.userId?.let {
            runTest {
                getActivityRegistry().clear(it)
            }
        }
    }

    fun getActivityRegistry(): ActivityRegistry {
        return requireNotNull(activityRegistry)
    }
}