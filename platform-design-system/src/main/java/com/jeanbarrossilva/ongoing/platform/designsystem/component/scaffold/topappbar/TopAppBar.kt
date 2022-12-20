package com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.topappbar

import android.content.res.Configuration
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.centerAlignedTopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.topappbar.component.NavigationButton
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

const val TOP_APP_BAR_TAG = "top_app_bar"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    style: TopAppBarStyle,
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit = { },
    actions: @Composable RowScope.() -> Unit = { }
) {
    val backgroundColor = MaterialTheme.colorScheme.primaryContainer

    CenterAlignedTopAppBar(
        title = { ProvideTextStyle(MaterialTheme.typography.titleSmall, title) },
        modifier.testTag(TOP_APP_BAR_TAG),
        navigationIcon = {
            if (style is TopAppBarStyle.Navigable) {
                NavigationButton(onClick = style::onNavigationRequest)
            }
        },
        actions,
        colors = centerAlignedTopAppBarColors(
            containerColor = backgroundColor,
            scrolledContainerColor = backgroundColor
        )
    )
}

@Composable
private fun TopAppBar(relevance: TopAppBarStyle, modifier: Modifier = Modifier) {
    TopAppBar(relevance, modifier, title = { Text("Title") })
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun RootTopAppBarPreview() {
    OngoingTheme {
        TopAppBar(TopAppBarStyle.Root)
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun NavigableTopAppBarPreview() {
    OngoingTheme {
        TopAppBar(TopAppBarStyle.Navigable { })
    }
}