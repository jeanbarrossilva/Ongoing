package com.jeanbarrossilva.ongoing.context.registry.domain.activity

import android.os.Parcelable
import com.jeanbarrossilva.ongoing.context.registry.extensions.uuid
import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.user.User
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class ContextualActivity(
    val id: String,
    val owner: User?,
    val name: String,
    val icon: ContextualIcon,
    val statuses: List<ContextualStatus>,
    val observers: List<User>,
    val isObserving: Boolean
): Parcelable, Serializable {
    val status
        get() = statuses.last()

    fun toActivity(): Activity {
        val icon = icon.toIcon()
        val statuses = statuses.map(ContextualStatus::toStatus)
        val observerUserIds = observers.map(User::id)
        return Activity(id, owner?.id, name, icon, statuses, observerUserIds)
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
                statuses = statuses.toList().ifEmpty { listOf(ContextualStatus.TO_DO) },
                observers = emptyList(),
                isObserving = false
            )
        }
    }
}