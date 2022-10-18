package com.jeanbarrossilva.ongoing.feature.activities

import android.content.Context
import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualActivity

interface ActivitiesBoundary {
    fun navigateToActivityDetails(context: Context, activity: ContextualActivity)
}