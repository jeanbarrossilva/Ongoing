package com.jeanbarrossilva.ongoing.context.registry.component.activityicon

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.context.registry.R
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme
import com.jeanbarrossilva.ongoing.platform.loadable.Loadable
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.ifLoaded

@Composable
fun ActivityIcon(
    activity: Loadable<ContextualActivity>,
    size: ActivityIconSize,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    val activityIcon = activity.ifLoaded(ContextualActivity::icon)?.vector
    val icon = if (isSelected) Icons.Rounded.Check else activityIcon
    val containerSize = activityIcon?.let { size.adaptedValue } ?: size.value
    val secondaryContainerColor = MaterialTheme.colorScheme.secondaryContainer
    val primaryColor = MaterialTheme.colorScheme.primary
    val backgroundColor by animateColorAsState(
        if (isSelected) primaryColor else secondaryContainerColor
    )
    val contentColor by animateColorAsState(
        if (isSelected) secondaryContainerColor else primaryColor
    )

    Box(
        modifier
            .clip(size.shape)
            .background(backgroundColor)
            .padding(size.padding)
            .size(containerSize)
    ) {
        icon?.let {
            Icon(
                it,
                contentDescription = stringResource(
                    R.string.platform_registry_activity_card_icon_content_description
                ),
                Modifier.size(size.adaptedValue),
                contentColor
            )
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun SelectedActivityIconPreview() {
    OngoingTheme {
        ActivityIcon(
            Loadable.Loaded(ContextualActivity.sample),
            ActivityIconSize.LARGE,
            isSelected = false
        )
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun UnselectedActivityIconPreview() {
    OngoingTheme {
        ActivityIcon(
            Loadable.Loaded(ContextualActivity.sample),
            ActivityIconSize.LARGE,
            isSelected = true
        )
    }
}