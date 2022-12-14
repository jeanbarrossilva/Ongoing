package com.jeanbarrossilva.ongoing.platform.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.TypographyTokens

internal val Typography = Typography(
    headlineSmall = TypographyTokens.HeadlineSmall.copy(fontWeight = FontWeight.Medium),
    titleLarge = TypographyTokens.TitleLarge.copy(fontWeight = FontWeight.Bold),
    titleSmall = TypographyTokens.TitleSmall.copy(
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    ),
    bodyLarge = TypographyTokens.BodyLarge.copy(fontSize = 14.sp)
)