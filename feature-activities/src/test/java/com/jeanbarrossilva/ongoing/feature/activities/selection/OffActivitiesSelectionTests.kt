package com.jeanbarrossilva.ongoing.feature.activities.selection

import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.feature.activities.ActivitiesSelection
import com.jeanbarrossilva.ongoing.feature.activities.extensions.ifOff
import com.jeanbarrossilva.ongoing.feature.activities.extensions.ifOn
import org.junit.Assert
import org.junit.Test

internal class OffActivitiesSelectionTests {
    @Test
    fun `GIVEN an activity WHEN toggling it THEN it is on`() {
        Assert.assertEquals(
            ActivitiesSelection.On(ContextualActivity.sample),
            ActivitiesSelection.Off.toggle(ContextualActivity.sample)
        )
    }

    @Test
    fun `GIVEN a call to ifOff WHEN getting the returned value THEN it is the transformation result`() {
        Assert.assertEquals("Hello, world!", ActivitiesSelection.Off.ifOff { "Hello, world!" })
    }

    @Test
    fun `GIVEN a call to ifOn WHEN getting the returned result THEN it is null`() {
        Assert.assertNull(ActivitiesSelection.Off.ifOn { "Hello, world!" })
    }
}