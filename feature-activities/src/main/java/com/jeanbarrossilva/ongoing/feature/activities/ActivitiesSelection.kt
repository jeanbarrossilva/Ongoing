package com.jeanbarrossilva.ongoing.feature.activities

import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.feature.activities.ActivitiesSelection.Off
import com.jeanbarrossilva.ongoing.feature.activities.ActivitiesSelection.Off.toggle
import com.jeanbarrossilva.ongoing.feature.activities.ActivitiesSelection.On
import com.jeanbarrossilva.ongoing.feature.activities.extensions.ifOff
import com.jeanbarrossilva.ongoing.feature.activities.extensions.ifOn
import com.jeanbarrossilva.ongoing.feature.activities.extensions.toggle

/**
 * Controls the selection of [contextual activities][ContextualActivity], aiming on a good UX.
 *
 * The expected usage is to provide an initial selection that's [off][Off] and to listen to the
 * event that'll turn it [on][On], such as a long press. And then, when the second event is fired
 * off (such as a tap) while the selection is [on][On], it selects or unselects the given
 * [ContextualActivity] (achievable through [On.toggle], that, in turn, returns the
 * selection that corresponds to the state after that change).
 *
 * This behavior can be easily implemented with the existent extension functions, [ifOn] and
 * [ifOff].
 **/
internal sealed class ActivitiesSelection {
    /**
     * A toggleable state that holds the currently selected [ContextualActivity].
     *
     * @param selected [Contextual activities][ContextualActivity] that are selected.
     **/
    data class On(val selected: List<ContextualActivity>): ActivitiesSelection() {
        constructor(): this(emptyList())

        constructor(activity: ContextualActivity): this(listOf(activity))

        override fun toggle(activity: ContextualActivity): ActivitiesSelection {
            val toggled = selected.toMutableList().apply { toggle(activity) }.toList()
            return if (toggled.isEmpty()) Off else On(toggled)
        }

        fun isSelected(activity: ContextualActivity): Boolean {
            return activity in selected
        }
    }

    /**
     * A static state whose only operation that can be performed is for it to be turned on through a
     * [toggle].
     **/
    object Off: ActivitiesSelection() {
        override fun toggle(activity: ContextualActivity): ActivitiesSelection {
            return On(activity)
        }
    }

    /**
     * Selects or unselects the given [activity] and then returns a selection according to the
     * result of the operation.
     *
     * @param activity [ContextualActivity] to toggle.
     **/
    abstract fun toggle(activity: ContextualActivity): ActivitiesSelection
}