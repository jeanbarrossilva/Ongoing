package com.jeanbarrossilva.ongoing.feature.activities.component.scaffold.topappbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import com.jeanbarrossilva.ongoing.context.user.ContextualUser
import com.jeanbarrossilva.ongoing.feature.activities.component.AvatarIcon

@Suppress("UnusedReceiverParameter")
@Composable
internal fun RowScope.TopAppBarDefaultActions(
    user: ContextualUser?,
    onSettingsRequest: () -> Unit
) {
    AvatarIcon(user, onClick = onSettingsRequest)
}