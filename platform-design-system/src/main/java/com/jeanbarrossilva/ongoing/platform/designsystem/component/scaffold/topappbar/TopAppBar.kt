package com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.topappbar

import android.content.res.Configuration
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.centerAlignedTopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.topappbar.component.NavigationButton
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    relevance: TopAppBarRelevance,
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit = { }
) {
    val backgroundColor = MaterialTheme.colorScheme.primaryContainer

    CenterAlignedTopAppBar(
        title = { ProvideTextStyle(relevance.textStyle, title) },
        modifier,
        navigationIcon = {
            if (relevance is TopAppBarRelevance.Subsequent) {
                NavigationButton(onClick = relevance.onNavigationRequest)
            }
        },
        colors = centerAlignedTopAppBarColors(
            containerColor = backgroundColor,
            scrolledContainerColor = backgroundColor
        )
    )
}

@Composable
private fun TopAppBar(relevance: TopAppBarRelevance, modifier: Modifier = Modifier) {
    TopAppBar(relevance, modifier) {
        Text("Title")
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun MainTopAppBarPreview() {
    OngoingTheme {
        TopAppBar(TopAppBarRelevance.Main)
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun SubsequentTopAppBarPreview() {
    OngoingTheme {
        TopAppBar(TopAppBarRelevance.Subsequent(onNavigationRequest = { }))
    }
}