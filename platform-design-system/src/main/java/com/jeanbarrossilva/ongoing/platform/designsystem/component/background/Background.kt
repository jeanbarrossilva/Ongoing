package com.jeanbarrossilva.ongoing.platform.designsystem.component.background

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

@Composable
fun Background(
    modifier: Modifier = Modifier,
    contentSizing: BackgroundContentSizing = BackgroundContentSizing.FILL,
    content: @Composable BoxScope.() -> Unit
) {
    Surface(color = MaterialTheme.colorScheme.background) {
        Box(modifier then contentSizing.modifier, content = content)
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun BackgroundPreview() {
    OngoingTheme {
        Background {
        }
    }
}