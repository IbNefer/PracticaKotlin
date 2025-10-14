package com.example.makeitso.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
  primary = BrightOrange,
  secondary = DarkOrange,
  tertiary = MediumOrange,
  background = Color(0xFF121212), // optional, default dark background
  surface = Color(0xFF1E1E1E),
  onPrimary = Color.White,
  onSecondary = Color.White,
  onBackground = Color.White,
  onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
  primary = BrightOrange,
  secondary = DarkOrange,
  tertiary = MediumOrange,
  background = Color.White,
  surface = Color.White,
  onPrimary = Color.White,
  onSecondary = Color.White,
  onBackground = Color.Black,
  onSurface = Color.Black
)

@Composable
fun MakeItSoTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  val colors = if (darkTheme) DarkColorScheme else LightColorScheme

  MaterialTheme(
    colorScheme = colors,
    typography = Typography,
    shapes = Shapes,
    content = content
  )
}
