package com.jeanbarrossilva.ongoing.core.registry

import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.registry.activity.Icon
import com.jeanbarrossilva.ongoing.core.registry.activity.status.Status
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ActivityTests {
    @Test
    fun `GIVEN an empty list of statuses WHEN creating an activity with it THEN it statuses are the default ones`() {
        val activity = Activity(
            uuid(),
            ownerUserId = uuid(),
            name = "Buy an airplane",
            Icon.OTHER,
            statuses = emptyList(),
            observerUserIds = emptyList()
        )
        assertEquals(Status.default, activity.statuses)
    }
}