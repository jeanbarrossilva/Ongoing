package com.jeanbarrossilva.ongoing.feature.activities.context

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.jeanbarrossilva.ongoing.feature.activities.R

internal enum class ContextualStatus {
    TO_DO {
        override val titleRes = R.string.feature_activities_full_status_title_to_do
    },
    ONGOING {
        override val titleRes = R.string.feature_activities_full_status_title_ongoing
    },
    DONE {
        override val titleRes = R.string.feature_activities_full_status_title_done
    };

    @get:StringRes
    protected abstract val titleRes: Int

    val title
        @Composable get() = stringResource(titleRes)

    companion object {
        val all = values().toList()
    }
}