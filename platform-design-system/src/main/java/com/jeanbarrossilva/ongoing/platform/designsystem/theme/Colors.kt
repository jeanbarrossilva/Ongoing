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
private val LightSecondaryContainer = Color(0xFFF8F8F8)
private val LightOnSecondaryContainer = Color.Black

private val DarkPrimary = Color(0xFF31A74E)
private val DarkOnPrimary = Color.Black
private val DarkSecondaryContainer = Color(0xFF424242)
private val DarkOnSecondaryContainer = Color.White

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
        secondaryContainer = LightSecondaryContainer,
        onSecondaryContainer = LightOnSecondaryContainer,
        background = background,
        surfaceVariant = LightSecondaryContainer,
        onSurfaceVariant = LightOnSecondaryContainer,
        surfaceTint = background
    )
private val darkColorScheme
    @Composable get() = darkColorScheme(
        DarkPrimary,
        DarkOnPrimary,
        primaryContainer,
        onPrimaryContainer = Color.White,
        secondaryContainer = DarkSecondaryContainer,
        onSecondaryContainer = DarkOnSecondaryContainer,
        background = background,
        surfaceVariant = DarkSecondaryContainer,
        onSurfaceVariant = DarkOnSecondaryContainer,
        surfaceTint = background
    )

internal val colors
    @Composable get() = if (isSystemInDarkTheme()) darkColorScheme else lightColorScheme