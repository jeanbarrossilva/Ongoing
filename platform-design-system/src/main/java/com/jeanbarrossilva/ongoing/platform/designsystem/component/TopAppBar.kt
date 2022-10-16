package com.jeanbarrossilva.ongoing.platform.designsystem.component

import android.content.res.Configuration
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults.centerAlignedTopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(title: @Composable () -> Unit, modifier: Modifier = Modifier) {
    val backgroundColor = MaterialTheme.colorScheme.primaryContainer

    CenterAlignedTopAppBar(
        title,
        modifier,
        colors = centerAlignedTopAppBarColors(
            containerColor = backgroundColor,
            scrolledContainerColor = backgroundColor
        )
    )
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun TopAppBarPreview() {
    OngoingTheme {
        com.jeanbarrossilva.ongoing.platform.designsystem.component.TopAppBar(title = {  })
    }
}