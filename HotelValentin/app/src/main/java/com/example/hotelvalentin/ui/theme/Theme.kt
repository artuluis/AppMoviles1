package com.example.hotelvalentin.ui.theme

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

private val DarkColorScheme = darkColorScheme(
    primary = HotelGoldLight,
    onPrimary = HotelNavy,
    secondary = HotelGold,
    background = Color(0xFF0F172A),
    surface = Color(0xFF1E293B),
    onSurface = Color(0xFFF1F5F9),
    onBackground = Color(0xFFF1F5F9)
)

private val LightColorScheme = lightColorScheme(
    primary = HotelGoldDark,
    onPrimary = Color.White,
    secondary = HotelNavy,
    onSecondary = Color.White,
    tertiary = HotelAccent,
    background = HotelWarmBg,
    surface = HotelCardBg,
    onSurface = HotelNavy,
    onBackground = HotelNavy
)

@Composable
fun HotelValentinTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Usamos la paleta exclusiva del Hotel por defecto
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