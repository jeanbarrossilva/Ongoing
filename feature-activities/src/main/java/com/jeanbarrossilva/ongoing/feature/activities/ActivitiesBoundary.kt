package com.jeanbarrossilva.ongoing.feature.activities

import android.content.Context
import kotlinx.coroutines.CoroutineScope

interface ActivitiesBoundary {
    fun navigateToAuthentication(context: Context)

    fun navigateToSettings(context: Context, coroutineScope: CoroutineScope)

    fun navigateToActivityDetails(context: Context, activityId: String)

    fun navigateToActivityEditing(context: Context)

    companion object {
        internal fun create(navigateToAuthentication: (Context) -> Unit = { }): ActivitiesBoundary {
            return object: ActivitiesBoundary {
                override fun navigateToAuthentication(context: Context) {
                    navigateToAuthentication(context)
                }

                override fun navigateToSettings(context: Context, coroutineScope: CoroutineScope) {
                }

                override fun navigateToActivityDetails(context: Context, activityId: String) {
                }

                override fun navigateToActivityEditing(context: Context) {
                }
            }
        }
    }
}