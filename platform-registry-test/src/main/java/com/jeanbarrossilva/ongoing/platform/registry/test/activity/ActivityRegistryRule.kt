package com.jeanbarrossilva.ongoing.platform.registry.test.activity

import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.platform.registry.OngoingDatabase
import com.jeanbarrossilva.ongoing.platform.registry.extensions.getActivityRegistry
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.rules.ExternalResource

internal class ActivityRegistryRule(
    private val session: Session,
    private val database: () -> OngoingDatabase
): ExternalResource() {
    private var activityRegistry: ActivityRegistry? = null

    override fun before() {
        activityRegistry = database().getActivityRegistry(session)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun after() {
        runTest {
            getActivityRegistry().clear(getCurrentUserId())
        }
    }

    fun getActivityRegistry(): ActivityRegistry {
        return requireNotNull(activityRegistry)
    }

    private suspend fun getCurrentUserId(): String {
        return requireNotNull(session.getUser().first()).id
    }
}