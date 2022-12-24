package com.jeanbarrossilva.ongoing.context.user.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.jeanbarrossilva.ongoing.context.user.ContextualUser
import com.jeanbarrossilva.ongoing.context.user.R
import com.jeanbarrossilva.ongoing.context.user.component.Avatar.shape
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

object Avatar {
    val shape = CircleShape
}

@Composable
fun Avatar(url: String?, modifier: Modifier = Modifier) {
    AsyncImage(
        url,
        contentDescription = stringResource(R.string.context_user_avatar_content_description),
        modifier
            .clip(shape)
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .aspectRatio(1f)
    )
}

@Composable
@Preview
private fun AvatarPreview() {
    OngoingTheme {
        Avatar(ContextualUser.sample.avatarUrl)
    }
}