package com.example.expensetracker.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = GreenPrimary,
    secondary = GreenSecondary,
    tertiary = GreenAccent,
    primaryContainer = Color(0xFFC8E6C9),
    secondaryContainer = Color(0xFFE8F5E9),
    
    background = LightBackground,
    surface = LightSurface,
    surfaceVariant = Color(0xFFF1F8F4),

    error = ExpenseRed,

    onPrimary = Color.White,
    onSecondary = Color.White,
    onPrimaryContainer = Color(0xFF1B5E20),
    onSecondaryContainer = Color(0xFF2E7D32),
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    onError = Color.White
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkGreenPrimary,
    secondary = GreenAccent,
    tertiary = GreenSecondary,
    primaryContainer = Color(0xFF2E7D32),
    secondaryContainer = Color(0xFF1B5E20),
    
    background = DarkBackground,
    surface = DarkSurface,
    surfaceVariant = Color(0xFF2C2C2C),

    error = ExpenseRed,

    onPrimary = Color(0xFF1B5E20),
    onSecondary = Color(0xFF1B5E20),
    onPrimaryContainer = Color(0xFFC8E6C9),
    onSecondaryContainer = Color(0xFFE8F5E9),
    onBackground = TextOnDark,
    onSurface = TextOnDark,
    onError = Color.White
)

@Composable
fun ExpenseTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}
