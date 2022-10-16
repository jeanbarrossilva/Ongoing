package com.jeanbarrossilva.ongoing.platform.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.jeanbarrossilva.ongoing.platform.designsystem.R

private val LightPrimary = Color(0xFF2DC653)
private val LightOnPrimary = Color.White

private val DarkPrimary = Color(0xFF31A74E)
private val DarkOnPrimary = Color.Black

private val background
    @Composable get() = colorResource(R.color.background)
private val primaryContainer
    @Composable get() = colorResource(R.color.primary_container)

private val lightColorScheme
    @Composable get() = lightColorScheme(
        LightPrimary,
        LightOnPrimary,
        primaryContainer,
        onPrimaryContainer = Color.Black,
        secondaryContainer = Color(0xFFF8F8F8),
        onSecondaryContainer = Color.Black,
        background = background
    )
private val darkColorScheme
    @Composable get() = darkColorScheme(
        DarkPrimary,
        DarkOnPrimary,
        primaryContainer,
        onPrimaryContainer = Color.White,
        secondaryContainer = Color(0xFF424242),
        onSecondaryContainer = Color.White,
        background = background
    )

internal val colors
    @Composable get() = if (isSystemInDarkTheme()) darkColorScheme else lightColorScheme