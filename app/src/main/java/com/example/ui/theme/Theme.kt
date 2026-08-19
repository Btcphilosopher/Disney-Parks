package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DisneyVariantDarkColor = Color(0xFF16253D)

private val DarkColorScheme = darkColorScheme(
    primary = CleanBlueLight,
    onPrimary = Color.White,
    primaryContainer = Slate800,
    onPrimaryContainer = CleanBlue100,
    secondary = DisneyGold,
    onSecondary = Slate900,
    secondaryContainer = DisneyVariantDarkColor,
    onSecondaryContainer = DisneyIceBlue,
    tertiary = Orange600,
    onTertiary = Color.White,
    background = SurfaceDark,
    onBackground = TextPrimaryDark,
    surface = CardDark,
    onSurface = TextPrimaryDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = TextSecondaryDark,
    outline = BorderDark
)

private val LightColorScheme = lightColorScheme(
    primary = CleanBlue,
    onPrimary = Color.White,
    primaryContainer = CleanBlue50,
    onPrimaryContainer = CleanBlueDark,
    secondary = DisneyGold,
    onSecondary = Slate900,
    secondaryContainer = Amber50,
    onSecondaryContainer = Amber600,
    tertiary = Orange600,
    onTertiary = Color.White,
    background = SurfaceLight,
    onBackground = TextPrimaryLight,
    surface = CardLight,
    onSurface = TextPrimaryLight,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = TextSecondaryLight,
    outline = Slate100
)

@Composable
fun DisneyParksTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
