package nl.mranderson.rijks.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Purple40,
    onPrimary = White,
    secondary = PurpleGrey40,
    surface = GraySurface,
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    onPrimary = White,
    secondary = PurpleGrey40,
    surface = White,
)

@Composable
fun RijksTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    val view = LocalView.current
    val window = (view.context as Activity).window

    val windowInsetController = WindowCompat.getInsetsController(window, view)

    windowInsetController.isAppearanceLightStatusBars = !darkTheme
    windowInsetController.isAppearanceLightNavigationBars = !darkTheme

    MaterialTheme(
        colorScheme = colors,
        content = content
    )

}
