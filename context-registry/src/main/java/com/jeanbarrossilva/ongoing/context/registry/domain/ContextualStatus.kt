package com.jeanbarrossilva.ongoing.context.registry.domain

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.jeanbarrossilva.ongoing.platform.registry.R

enum class ContextualStatus {
    TO_DO {
        override val titleRes = R.string.platform_registry_contextual_status_title_to_do
    },
    ONGOING {
        override val titleRes = R.string.platform_registry_contextual_status_title_ongoing
    },
    DONE {
        override val titleRes = R.string.platform_registry_contextual_status_title_done
    };

    @get:StringRes
    protected abstract val titleRes: Int

    val title
        @Composable get() = stringResource(titleRes)

    companion object {
        val all = values().toList()
    }
}