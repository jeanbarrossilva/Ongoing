package com.jeanbarrossilva.ongoing.platform.registry.test

import com.jeanbarrossilva.ongoing.core.session.inmemory.InMemorySession
import com.jeanbarrossilva.ongoing.platform.registry.test.activity.ActivityRegistryRule
import com.jeanbarrossilva.ongoing.platform.registry.test.database.OngoingDatabaseRule
import com.jeanbarrossilva.ongoing.platform.registry.test.session.SessionRule
import org.junit.rules.RuleChain
import org.junit.rules.TestRule

class PlatformRegistryTestRule private constructor(
    private val sessionRule: SessionRule,
    private val activityRegistryRule: ActivityRegistryRule,
    private val databaseRule: OngoingDatabaseRule,
): TestRule by RuleChain.outerRule(sessionRule).around(databaseRule).around(activityRegistryRule) {
    val activityRegistry
        get() = activityRegistryRule.getActivityRegistry()
    val session
        get() = sessionRule.session

    companion object {
        fun create(): PlatformRegistryTestRule {
            val session = InMemorySession()
            val sessionRule = SessionRule(session)
            val databaseRule = OngoingDatabaseRule()
            val activityRegistryRule = ActivityRegistryRule(session, databaseRule::database)
            return PlatformRegistryTestRule(sessionRule, activityRegistryRule, databaseRule)
        }
    }
}