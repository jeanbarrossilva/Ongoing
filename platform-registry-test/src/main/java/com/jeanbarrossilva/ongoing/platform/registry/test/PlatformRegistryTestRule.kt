package com.jeanbarrossilva.ongoing.platform.registry.test

import com.jeanbarrossilva.ongoing.core.session.inmemory.InMemorySessionManager
import com.jeanbarrossilva.ongoing.platform.registry.test.activity.ActivityRegistryRule
import com.jeanbarrossilva.ongoing.platform.registry.test.database.OngoingDatabaseRule
import com.jeanbarrossilva.ongoing.platform.registry.test.sessionmanager.SessionManagerRule
import org.junit.rules.RuleChain
import org.junit.rules.TestRule

class PlatformRegistryTestRule private constructor(
    private val sessionManagerRule: SessionManagerRule,
    private val activityRegistryRule: ActivityRegistryRule,
    private val databaseRule: OngoingDatabaseRule,
): TestRule by RuleChain.outerRule(sessionManagerRule).around(databaseRule).around(activityRegistryRule) {
    val activityRegistry
        get() = activityRegistryRule.getActivityRegistry()
    val sessionManager
        get() = sessionManagerRule.sessionManager

    companion object {
        fun create(): PlatformRegistryTestRule {
            val session = InMemorySessionManager()
            val sessionManagerRule = SessionManagerRule(session)
            val databaseRule = OngoingDatabaseRule()
            val activityRegistryRule = ActivityRegistryRule(session, databaseRule::database)
            return PlatformRegistryTestRule(sessionManagerRule, activityRegistryRule, databaseRule)
        }
    }
}