package com.jeanbarrossilva.ongoing.platform.registry

import com.jeanbarrossilva.ongoing.platform.registry.activity.registry.UnregisteringResult
import org.junit.Assert.assertEquals
import org.junit.Test

internal class UnregisteringResultTests {
    @Test
    fun `GIVEN an existing activity WHEN unregistering it as its owner THEN it's allowed`() {
        assertEquals(
            UnregisteringResult.Allowed,
            UnregisteringResult.of(isActivityExistent = true, isActivityOwner = true)
        )
    }

    @Test
    fun `GIVEN an existing activity WHEN unregistering it not being its owner THEN it's denied`() {
        assertEquals(
            UnregisteringResult.Denied,
            UnregisteringResult.of(isActivityExistent = true, isActivityOwner = false)
        )
    }

    @Test
    fun `GIVEN a nonexistent activity WHEN unregistering it not being its owner THEN it's nonexistent`() {
        assertEquals(
            UnregisteringResult.Nonexistent,
            UnregisteringResult.of(isActivityExistent = false, isActivityOwner = false)
        )
    }

    @Test(expected = IllegalStateException::class)
    fun `GIVEN a nonexistent activity WHEN unregistering it as its owner THEN it throws`() {
        UnregisteringResult.of(isActivityExistent = false, isActivityOwner = true)
    }
}