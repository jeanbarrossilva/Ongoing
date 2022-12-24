package com.jeanbarrossilva.ongoing.feature.activities.component.scaffold.topappbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import com.jeanbarrossilva.ongoing.feature.activities.ActivitiesOwner
import com.jeanbarrossilva.ongoing.feature.activities.component.OwnerAvatar
import com.jeanbarrossilva.ongoing.platform.loadable.Loadable
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.ifLoaded

@Suppress("UnusedReceiverParameter")
@Composable
internal fun RowScope.TopAppBarDefaultActions(
    owner: Loadable<ActivitiesOwner?>,
    onSettingsRequest: () -> Unit
) {
    owner.ifLoaded {
        OwnerAvatar(this, onClick = onSettingsRequest)
    }
}