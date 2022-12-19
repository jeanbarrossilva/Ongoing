package com.jeanbarrossilva.ongoing.feature.activities.component.scaffold.topappbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import com.jeanbarrossilva.ongoing.core.session.user.User
import com.jeanbarrossilva.ongoing.feature.activities.component.AvatarIcon

@Suppress("UnusedReceiverParameter")
@Composable
internal fun RowScope.TopAppBarDefaultActions(user: User?, onSettingsRequest: () -> Unit) {
    AvatarIcon(user, onClick = onSettingsRequest)
}