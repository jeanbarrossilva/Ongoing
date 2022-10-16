package com.jeanbarrossilva.ongoing.feature.activities.context

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Book
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.ui.graphics.vector.ImageVector

internal enum class ContextualIcon {
    BOOK {
        override val vector = Icons.Rounded.Book
    },
    OTHER {
        override val vector = Icons.Rounded.MoreHoriz
    };

    abstract val vector: ImageVector
}