package com.jeanbarrossilva.ongoing.feature.activities

import android.content.Context
import com.jeanbarrossilva.ongoing.context.user.ContextualUser

interface ActivitiesBoundary {
    fun navigateToAuthentication(context: Context)

    fun navigateToSettings(context: Context, user: ContextualUser)

    fun navigateToActivityDetails(context: Context, activityId: String)

    fun navigateToActivityEditing(context: Context)

    companion object {
        internal val empty = object: ActivitiesBoundary {
            override fun navigateToAuthentication(context: Context) {
            }

            override fun navigateToSettings(context: Context, user: ContextualUser) {
            }

            override fun navigateToActivityDetails(context: Context, activityId: String) {
            }

            override fun navigateToActivityEditing(context: Context) {
            }
        }
    }
}