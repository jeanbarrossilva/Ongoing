package com.jeanbarrossilva.ongoing.feature.activitydetails.extensions

import android.content.Context
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualIcon
import com.jeanbarrossilva.ongoing.core.session.inmemory.InMemorySessionManager
import com.jeanbarrossilva.ongoing.feature.activitydetails.domain.ContextActivity

/** [ContextualIcon] that matches the [ContextActivity.iconName]. **/
internal val ContextActivity.contextualIcon
    get() = ContextualIcon.valueOf(iconName)

/** Creates a sample [ContextActivity]. **/
internal fun ContextActivity.Companion.createSample(context: Context): ContextActivity {
    val sessionManager = InMemorySessionManager()
    return ContextualActivity.sample.toContextActivity(sessionManager, context)
}