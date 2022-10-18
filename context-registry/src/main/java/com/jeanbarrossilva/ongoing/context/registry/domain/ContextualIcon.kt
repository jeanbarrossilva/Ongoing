package com.jeanbarrossilva.ongoing.context.registry.domain

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Book
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.ui.graphics.vector.ImageVector

enum class ContextualIcon {
    BOOK {
        override val vector = Icons.Rounded.Book
    },
    OTHER {
        override val vector = Icons.Rounded.MoreHoriz
    };

    abstract val vector: ImageVector
}