package com.jeanbarrossilva.ongoing.platform.designsystem.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.platform.designsystem.component.background.Background
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.floatingactionbutton.FloatingActionButton
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.topappbar.TopAppBar
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.topappbar.TopAppBarStyle
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme
import com.jeanbarrossilva.ongoing.platform.designsystem.component.Scaffold as _Scaffold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Scaffold(
    topBar: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    floatingActionButton: @Composable () -> Unit = { },
    content: @Composable (padding: PaddingValues) -> Unit
) {
    Scaffold(
        modifier,
        topBar,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = FabPosition.Center,
        content = content
    )
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ScaffoldPreview() {
    OngoingTheme {
        _Scaffold(
            topBar = { TopAppBar(TopAppBarStyle.Root, title = { Text("Title") }) },
            floatingActionButton = {
                FloatingActionButton(onClick = { }) {
                    Icon(Icons.Rounded.Add, contentDescription = "Add")
                }
            }
        ) {
            Background {
                Text("Content", Modifier.align(Alignment.Center))
            }
        }
    }
}