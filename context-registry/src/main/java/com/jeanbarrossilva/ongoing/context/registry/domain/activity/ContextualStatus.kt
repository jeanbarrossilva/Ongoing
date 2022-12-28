package com.jeanbarrossilva.ongoing.context.registry.domain.activity

import androidx.annotation.StringRes
import com.jeanbarrossilva.ongoing.context.registry.R
import com.jeanbarrossilva.ongoing.core.registry.activity.status.Status

enum class ContextualStatus {
    TO_DO {
        override val titleRes = R.string.context_registry_contextual_status_title_to_do

        override fun toStatus(): Status {
            return Status.TO_DO
        }
    },
    ONGOING {
        override val titleRes = R.string.context_registry_contextual_status_title_ongoing

        override fun toStatus(): Status {
            return Status.ONGOING
        }
    },
    DONE {
        override val titleRes = R.string.context_registry_contextual_status_title_done

        override fun toStatus(): Status {
            return Status.DONE
        }
    };

    @get:StringRes
    abstract val titleRes: Int

    abstract fun toStatus(): Status

    companion object {
        val values = values().toList()
    }
}