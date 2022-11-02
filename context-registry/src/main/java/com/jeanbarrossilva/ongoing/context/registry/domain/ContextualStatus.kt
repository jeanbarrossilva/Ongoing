package com.jeanbarrossilva.ongoing.context.registry.domain

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.jeanbarrossilva.ongoing.context.registry.R
import com.jeanbarrossilva.ongoing.core.registry.activity.Status

enum class ContextualStatus {
    TO_DO {
        override val titleRes = R.string.platform_registry_contextual_status_title_to_do

        override fun toStatus(): Status {
            return Status.TO_DO
        }
    },
    ONGOING {
        override val titleRes = R.string.platform_registry_contextual_status_title_ongoing

        override fun toStatus(): Status {
            return Status.ONGOING
        }
    },
    DONE {
        override val titleRes = R.string.platform_registry_contextual_status_title_done

        override fun toStatus(): Status {
            return Status.DONE
        }
    };

    @get:StringRes
    protected abstract val titleRes: Int

    val title
        @Composable get() = stringResource(titleRes)

    abstract fun toStatus(): Status

    companion object {
        val values = values().toList()
    }
}