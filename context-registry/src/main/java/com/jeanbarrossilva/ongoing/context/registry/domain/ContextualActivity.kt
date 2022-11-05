package com.jeanbarrossilva.ongoing.context.registry.domain

import android.os.Parcelable
import com.jeanbarrossilva.ongoing.core.session.user.User
import com.jeanbarrossilva.ongoing.context.registry.extensions.uuid
import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContextualActivity(
    val id: String,
    val owner: User?,
    val name: String,
    val icon: ContextualIcon,
    val statuses: List<ContextualStatus>
): Parcelable {
    val status
        get() = statuses.last()

    fun toActivity(): Activity {
        val icon = icon.toIcon()
        val statuses = statuses.map(ContextualStatus::toStatus)
        return Activity(id, owner?.id, name, icon, statuses)
    }

    companion object {
        val samples = listOf(
            sampleOf(name = "Go to History class", ContextualIcon.BOOK),
            sampleOf(name = "Practice for football finals"),
            sampleOf(name = "Create a new app"),
            sampleOf(name = "Buy a water bottle", statuses = arrayOf(ContextualStatus.DONE))
        )
        val sample = samples.first()

        private fun sampleOf(
            name: String,
            icon: ContextualIcon = ContextualIcon.BOOK,
            vararg statuses: ContextualStatus
        ): ContextualActivity {
            return ContextualActivity(
                uuid(),
                User.sample,
                name,
                icon,
                statuses = statuses.toList().ifEmpty { listOf(ContextualStatus.TO_DO) }
            )
        }
    }
}