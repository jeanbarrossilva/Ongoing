package com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults.elevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.FloatingActionButton as _FloatingActionButton

@Composable
fun FloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    FloatingActionButton(
        onClick,
        modifier,
        containerColor = MaterialTheme.colorScheme.primary,
        elevation = elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            focusedElevation = 0.dp
        ),
        content = content
    )
}

@Composable
@Preview
private fun FloatingActionButtonPreview() {
    OngoingTheme {
        _FloatingActionButton(onClick = { }) {
            Icon(Icons.Rounded.Add, contentDescription = "Add")
        }
    }
}