package com.jeanbarrossilva.ongoing.platform.designsystem.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.List
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jeanbarrossilva.ongoing.platform.designsystem.component.background.Background
import com.jeanbarrossilva.ongoing.platform.designsystem.component.background.BackgroundContentSizing
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.ContentAlpha
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.Size
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

@Composable
fun Message(
    icon: ImageVector,
    title: @Composable () -> Unit,
    description: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier, Arrangement.spacedBy(Size.Spacing.xxxxxl), Alignment.CenterHorizontally) {
        CompositionLocalProvider(
            LocalContentColor provides LocalContentColor.current.copy(ContentAlpha.MEDIUM)
        ) {
            Icon(icon, contentDescription = null, Modifier.size(64.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(Size.Spacing.xs),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProvideTextStyle(
                    MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center),
                    title
                )

                ProvideTextStyle(
                    MaterialTheme.typography.titleMedium.copy(
                        LocalContentColor.current.copy(ContentAlpha.DISABLED),
                        textAlign = TextAlign.Center
                    ),
                    description
                )
            }
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun MessagePreview() {
    OngoingTheme {
        Background(contentSizing = BackgroundContentSizing.WRAP) {
            Message(
                Icons.Rounded.List,
                title = { Text("Here goes the title...") },
                description = {
                    Text(
                        "...and then, here, a description. An explanation or some instructions " +
                        "for the user on how to proceed."
                    )
                }
            )
        }
    }
}