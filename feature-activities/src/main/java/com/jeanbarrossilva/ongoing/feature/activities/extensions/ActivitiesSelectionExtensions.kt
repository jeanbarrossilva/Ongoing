package com.jeanbarrossilva.ongoing.feature.activities.extensions

import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.feature.activities.ActivitiesSelection

/**
 * Whether or not the given [activity] is in the [ActivitiesSelection].
 *
 * @param activity [ContextualActivity] whose presence will be checked.
 **/
internal operator fun ActivitiesSelection.contains(activity: ContextualActivity): Boolean {
    return ifOn { isSelected(activity) } ?: false
}

/**
 * Returns the result of the given [transform] if this is [off][ActivitiesSelection.Off].
 *
 * @param transform Operation to be made on [ActivitiesSelection.Off].
 **/
internal inline fun <T> ActivitiesSelection.ifOff(transform: ActivitiesSelection.Off.() -> T): T? {
    return when (this) {
        is ActivitiesSelection.On -> null
        is ActivitiesSelection.Off -> transform()
    }
}

/**
 * Returns the result of the given [transform] if this is [on][ActivitiesSelection.On].
 *
 * @param transform Operation to be made on [ActivitiesSelection.On].
 **/
internal inline fun <T> ActivitiesSelection.ifOn(transform: ActivitiesSelection.On.() -> T): T? {
    return when (this) {
        is ActivitiesSelection.On -> transform()
        is ActivitiesSelection.Off -> null
    }
}