package com.jeanbarrossilva.ongoing.context.registry.domain

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Book
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.ui.graphics.vector.ImageVector
import com.jeanbarrossilva.ongoing.core.registry.activity.Icon

enum class ContextualIcon {
    BOOK {
        override val vector = Icons.Rounded.Book

        override fun toIcon(): Icon {
            return Icon.BOOK
        }
    },
    OTHER {
        override val vector = Icons.Rounded.MoreHoriz

        override fun toIcon(): Icon {
            return Icon.OTHER
        }
    };

    abstract val vector: ImageVector

    abstract fun toIcon(): Icon
}