package com.jeanbarrossilva.ongoing.feature.activities.selection

import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.feature.activities.ActivitiesSelection
import com.jeanbarrossilva.ongoing.feature.activities.extensions.ifOff
import com.jeanbarrossilva.ongoing.feature.activities.extensions.ifOn
import org.junit.Assert
import org.junit.Test

internal class OnActivitiesSelectionTests {
    @Test
    fun `GIVEN an unselected activity WHEN toggling it THEN it is selected`() {
        Assert.assertEquals(
            ActivitiesSelection.On(ContextualActivity.sample),
            ActivitiesSelection.On().toggle(ContextualActivity.sample)
        )
    }

    @Test
    fun `GIVEN a selected activity WHEN toggling it THEN it is unselected`() {
        // Although they look the same, each call to the sample activity generates one with a
        // different ID. Maybe it should be a function instead?
        val remainingActivity = ContextualActivity.sample
        val toggledActivity = ContextualActivity.sample

        Assert.assertEquals(
            ActivitiesSelection.On(remainingActivity),
            ActivitiesSelection.On(listOf(remainingActivity, toggledActivity))
                .toggle(toggledActivity)
        )
    }

    @Test
    fun `GIVEN a call to ifOff WHEN getting the returned value THEN it is null`() {
        Assert.assertNull(ActivitiesSelection.On().ifOff { "Hello, world!" })
    }

    @Test
    fun `GIVEN a call to ifOn WHEN getting the returned value THEN it is the transformation result`() {
        Assert.assertEquals("Hello, world!", ActivitiesSelection.On().ifOn { "Hello, world!" })
    }
}