package com.jeanbarrossilva.ongoing.context.registry.component.activityicon

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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
    modifier: Modifier = Modifier
) {
    val vector = activity.ifLoaded(ContextualActivity::icon)?.vector
    val containerSize = vector?.let { size.adaptedValue } ?: size.value

    Box(
        modifier
            .clip(size.shape)
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(size.padding)
            .size(containerSize)
    ) {
        vector?.let {
            Icon(
                it,
                contentDescription = stringResource(
                    R.string.platform_registry_activity_card_icon_content_description
                ),
                Modifier.size(size.adaptedValue),
                MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun SmallActivityIconPreview() {
    OngoingTheme {
        ActivityIcon(Loadable.Loaded(ContextualActivity.sample), ActivityIconSize.SMALL)
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun LargeActivityIconPreview() {
    OngoingTheme {
        ActivityIcon(Loadable.Loaded(ContextualActivity.sample), ActivityIconSize.LARGE)
    }
}