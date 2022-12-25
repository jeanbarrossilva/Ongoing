package com.jeanbarrossilva.ongoing.context.registry.domain.observation

import android.content.Context
import androidx.annotation.StringRes
import com.jeanbarrossilva.ongoing.context.registry.R
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualStatus

sealed class ContextualChange {
    @get:StringRes
    protected abstract val messageRes: Int

    data class Name(private val old: String, private val new: String): ContextualChange() {
        override val messageRes = R.string.context_registry_contextual_change_name

        override fun getMessageReplacements(context: Context, activity: ContextualActivity):
            Array<String?> {
            return arrayOf(activity.owner.name, old, new)
        }
    }

    data class Status(private val old: ContextualStatus?, private val new: ContextualStatus):
        ContextualChange() {
        override val messageRes = R.string.context_registry_contextual_change_status

        override fun getMessageReplacements(context: Context, activity: ContextualActivity):
            Array<String?> {
            return arrayOf(activity.owner.name, activity.name, context.getString(new.titleRes))
        }
    }

    fun getMessage(context: Context, activity: ContextualActivity): String {
        return context.getString(messageRes, *getMessageReplacements(context, activity))
    }

    protected abstract fun getMessageReplacements(context: Context, activity: ContextualActivity):
        Array<String?>
}