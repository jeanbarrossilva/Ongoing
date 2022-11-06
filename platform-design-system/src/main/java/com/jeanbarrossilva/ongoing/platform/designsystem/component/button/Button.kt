package com.jeanbarrossilva.ongoing.platform.designsystem.component.button

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.Size
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme
import com.jeanbarrossilva.ongoing.platform.designsystem.component.button.Button as _Button

@Composable
fun Button(
    onClick: () -> Unit,
    modifier: Modifier,
    relevance: ButtonRelevance = ButtonRelevance.Primary(),
    icon: @Composable () -> Unit,
    label: @Composable () -> Unit
) {
    _Button(onClick, modifier, relevance) {
        icon()
        Spacer(Modifier.width(Size.Spacing.xl))
        label()
    }
}

@Composable
fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    relevance: ButtonRelevance = ButtonRelevance.Primary(),
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick,
        modifier,
        shape = MaterialTheme.shapes.medium,
        colors = buttonColors(relevance.containerColor, relevance.contentColor),
        contentPadding = PaddingValues(horizontal = Size.Spacing.xxxl, vertical = Size.Spacing.xxl)
    ) {
        ProvideTextStyle(MaterialTheme.typography.titleSmall) {
            content()
        }
    }
}

@Composable
private fun Button(type: ButtonRelevance, modifier: Modifier = Modifier) {
    _Button(
        onClick = { },
        modifier,
        type,
        icon = { Icon(Icons.Rounded.Add, contentDescription = "Add") }
    ) {
        Text("Add")
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun PrimaryDefaultButtonPreview() {
    OngoingTheme {
        _Button(ButtonRelevance.Primary())
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun PrimaryImportantButtonPreview() {
    OngoingTheme {
        _Button(ButtonRelevance.Primary(ButtonPriority.IMPORTANT))
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun SecondaryDefaultButtonPreview() {
    OngoingTheme {
        _Button(ButtonRelevance.Secondary())
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun SecondaryImportantButtonPreview() {
    OngoingTheme {
        _Button(ButtonRelevance.Secondary(ButtonPriority.IMPORTANT))
    }
}