package theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.tkuenneth.nativeparameterstoreaccess.Dconf
import com.github.tkuenneth.nativeparameterstoreaccess.Dconf.HAS_DCONF
import com.github.tkuenneth.nativeparameterstoreaccess.MacOSDefaults
import com.github.tkuenneth.nativeparameterstoreaccess.NativeParameterStoreAccess.IS_MACOS
import com.github.tkuenneth.nativeparameterstoreaccess.NativeParameterStoreAccess.IS_WINDOWS
import com.github.tkuenneth.nativeparameterstoreaccess.WindowsRegistry
import kotlinx.coroutines.*

val CalibreColor = Color(0xff411540)
val DarkAppBarColor = Color(0xff1a1b1e)
val DarkBackground = Color(0xff1b1d21)
val LineColorLight = Color.Black.copy(alpha = 0.4f)
val LineColorDark = Color.White.copy(alpha = 0.3f)


private val LightColorPalette = CalibreColorPalette(
    brand = CalibreColor,
    uiBackground = Color.White,
    textPrimary = Color.Black,
    isDark = false,
    buttonColor = Color.Black,
    buttonTextColor = Color.White,
    appBarColor = CalibreColor,
    lineColor = LineColorLight,
    appBarTextTitleColor = Color.White,
    appBarTextSubTitleColor = Color.LightGray,
)

private val DarkColorPalette = CalibreColorPalette(
    brand = CalibreColor,
    uiBackground = DarkBackground,
    textPrimary = Color.White,
    isDark = true,
    buttonColor = Color.White,
    buttonTextColor = Color.Black,
    appBarColor = DarkAppBarColor,
    lineColor = LineColorDark,
    appBarTextTitleColor = Color.White,
    appBarTextSubTitleColor = Color.LightGray,
)

val CalibreShapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(6.dp),
    large = RoundedCornerShape(10.dp)
)

fun isSystemInDarkTheme(): Boolean = when {
    IS_WINDOWS -> {
        val result = WindowsRegistry.getWindowsRegistryEntry(
            "HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Themes\\Personalize",
            "AppsUseLightTheme"
        )
        result == 0x0
    }
    IS_MACOS -> {
        val result = MacOSDefaults.getDefaultsEntry("AppleInterfaceStyle")
        result == "Dark"
    }
    HAS_DCONF -> {
        val result = Dconf.getDconfEntry("/org/gnome/desktop/interface/gtk-theme")
        result.toLowerCase().contains("dark")
    }
    else -> false
}

@Composable
fun CalibreTheme(
    content: @Composable () -> Unit
) {
    var isInDarkMode by remember {
        mutableStateOf(isSystemInDarkTheme())
    }

    val colors = calibreColorPalette(isInDarkMode)

    LaunchedEffect(Unit) {
        this.launch {
            while (isActive) {
                val newMode = isSystemInDarkTheme()
                if (isInDarkMode != newMode) {
                    isInDarkMode = newMode
                }
                delay(1000)
            }
        }
    }

    CompositionLocalProvider(LocalCalibreColor provides colors, content = {
        MaterialTheme(
            colors = debugColors(isInDarkMode),
            shapes = CalibreShapes,
            content = content
        )
    })
}

private fun calibreColorPalette(isInDarkMode: Boolean) =
    if (isInDarkMode) DarkColorPalette else LightColorPalette

object CalibreColorProvider {
    val colors: CalibreColorPalette
        @Composable
        get() = LocalCalibreColor.current
}

class CalibreColorPalette(
    brand: Color,
    uiBackground: Color,
    textPrimary: Color = brand,
    isDark: Boolean,
    buttonColor: Color,
    buttonTextColor: Color,
    appBarColor: Color,
    lineColor: Color,
    appBarTextTitleColor: Color,
    appBarTextSubTitleColor: Color,
) {
    var uiBackground by mutableStateOf(uiBackground)
        private set
    var textPrimary by mutableStateOf(textPrimary)
        private set
    var isDark by mutableStateOf(isDark)
        private set
    var buttonColor by mutableStateOf(buttonColor)
        private set
    var buttonTextColor by mutableStateOf(buttonTextColor)
        private set
    var appBarColor by mutableStateOf(appBarColor)
        private set
    var lineColor by mutableStateOf(lineColor)
        private set

    var appBarTextTitleColor by mutableStateOf(appBarTextTitleColor)
        private set
    var appBarTextSubTitleColor by mutableStateOf(appBarTextSubTitleColor)
        private set
}

private val LocalCalibreColor = staticCompositionLocalOf<CalibreColorPalette> {
    error("No CalibreColorPalette provided")
}

/**
 * A Material [Colors] implementation which sets all colors to [debugColor] to discourage usage of
 * [MaterialTheme.colors] in preference to [CalibreColorProvider.colors].
 */
fun debugColors(
    darkTheme: Boolean,
    debugColor: Color = Color.Red
) = Colors(
    primary = debugColor,
    primaryVariant = debugColor,
    secondary = debugColor,
    secondaryVariant = debugColor,
    background = debugColor,
    surface = debugColor,
    error = debugColor,
    onPrimary = debugColor,
    onSecondary = debugColor,
    onBackground = debugColor,
    onSurface = debugColor,
    onError = debugColor,
    isLight = !darkTheme
)