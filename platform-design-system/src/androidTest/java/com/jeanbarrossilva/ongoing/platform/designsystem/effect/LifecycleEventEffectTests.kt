package com.jeanbarrossilva.ongoing.platform.designsystem.effect

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.testing.TestLifecycleOwner
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

internal class LifecycleEventEffectTests {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun notifyObserverOfEventChange() {
        val lifecycle = TestLifecycleOwner().lifecycle
        var currentEffectEvent = Lifecycle.Event.ON_ANY
        val expectedEvent = Lifecycle.Event.ON_START
        composeRule.setContent {
            LifecycleEventEffect(lifecycle) { _, event ->
                currentEffectEvent = event
            }
        }
        lifecycle.handleLifecycleEvent(expectedEvent)
        assertEquals(expectedEvent, currentEffectEvent)
    }
}