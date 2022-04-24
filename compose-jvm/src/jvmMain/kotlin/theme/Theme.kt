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
val FunctionalRed = Color(0xffd00036)
val FunctionalRedDark = Color(0xffea6d7e)
val CalibreLogoYellow = Color(0xffECB22E)
val LineColorLight = Color.Black.copy(alpha = 0.4f)
val LineColorDark = Color.White.copy(alpha = 0.3f)
const val AlphaNearOpaque = 0.95f
const val AlphaNearTransparent = 0.15f


private val LightColorPalette = CalibreColorPalette(
    brand = CalibreColor,
    accent = CalibreColor,
    uiBackground = Color.White,
    textPrimary = Color.Black,
    textSecondary = Color.DarkGray,
    error = FunctionalRed,
    statusBarColor = CalibreColor,
    isDark = false,
    buttonColor = Color.Black,
    buttonTextColor = Color.White,
    darkBackground = DarkBackground,
    appBarColor = CalibreColor,
    lineColor = LineColorLight,
    bottomNavSelectedColor = Color.Black,
    bottomNavUnSelectedColor = Color.LightGray,
    appBarIconColor = Color.White,
    appBarTextTitleColor = Color.White,
    appBarTextSubTitleColor = Color.LightGray,
    sendButtonDisabled = Color.LightGray,
    sendButtonEnabled = Color.Black
)

private val DarkColorPalette = CalibreColorPalette(
    brand = CalibreColor,
    accent = CalibreColor,
    uiBackground = DarkBackground,
    textPrimary = Color.White,
    textSecondary = Color.White,
    error = FunctionalRedDark,
    statusBarColor = CalibreColor,
    isDark = true,
    buttonColor = Color.White,
    buttonTextColor = Color.Black,
    darkBackground = DarkBackground,
    appBarColor = DarkAppBarColor,
    lineColor = LineColorDark,
    bottomNavSelectedColor = Color.White,
    bottomNavUnSelectedColor = Color.Gray,
    appBarIconColor = Color.White,
    appBarTextTitleColor = Color.White,
    appBarTextSubTitleColor = Color.LightGray,
    sendButtonDisabled = Color.White.copy(alpha = 0.4f),
    sendButtonEnabled = Color.White
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
    var isInDarkMode by remember{
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


    ProvideCalibreColors(colors) {
        MaterialTheme(
            colors = debugColors(isInDarkMode),
            shapes = CalibreShapes,
            content = content
        )
    }
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
    accent: Color,
    uiBackground: Color,
    textPrimary: Color = brand,
    textSecondary: Color,
    error: Color,
    statusBarColor: Color,
    isDark: Boolean,
    buttonColor: Color,
    buttonTextColor: Color,
    darkBackground: Color,
    appBarColor: Color,
    lineColor: Color,
    bottomNavSelectedColor: Color,
    bottomNavUnSelectedColor: Color,
    appBarIconColor: Color,
    appBarTextTitleColor: Color,
    appBarTextSubTitleColor: Color,
    sendButtonDisabled: Color,
    sendButtonEnabled: Color
) {
    var brand by mutableStateOf(brand)
        private set
    var accent by mutableStateOf(accent)
        private set
    var uiBackground by mutableStateOf(uiBackground)
        private set
    var statusBarColor by mutableStateOf(statusBarColor)
        private set
    var textPrimary by mutableStateOf(textPrimary)
        private set
    var textSecondary by mutableStateOf(textSecondary)
        private set
    var error by mutableStateOf(error)
        private set
    var isDark by mutableStateOf(isDark)
        private set
    var buttonColor by mutableStateOf(buttonColor)
        private set
    var buttonTextColor by mutableStateOf(buttonTextColor)
        private set
    var darkBackground by mutableStateOf(darkBackground)
        private set
    var appBarColor by mutableStateOf(appBarColor)
        private set
    var lineColor by mutableStateOf(lineColor)
        private set

    var bottomNavSelectedColor by mutableStateOf(bottomNavSelectedColor)
        private set
    var bottomNavUnSelectedColor by mutableStateOf(bottomNavUnSelectedColor)
        private set
    var appBarIconColor by mutableStateOf(appBarIconColor)
        private set

    var appBarTextTitleColor by mutableStateOf(appBarTextTitleColor)
        private set
    var appBarTextSubTitleColor by mutableStateOf(appBarTextSubTitleColor)
        private set
    var sendButtonDisabled by mutableStateOf(sendButtonDisabled)
        private set

    var sendButtonEnabled by mutableStateOf(sendButtonEnabled)
        private set


    fun update(other: CalibreColorPalette) {
        brand = other.brand
        uiBackground = other.uiBackground
        textPrimary = other.textPrimary
        textSecondary = other.textSecondary
        error = other.error
        statusBarColor = other.statusBarColor
        isDark = other.isDark
        buttonColor = other.buttonColor
        buttonTextColor = other.buttonTextColor
        darkBackground = other.darkBackground
        appBarColor = other.appBarColor
        lineColor = other.lineColor
        bottomNavSelectedColor = other.bottomNavSelectedColor
        bottomNavUnSelectedColor = other.bottomNavUnSelectedColor
        appBarIconColor = other.appBarIconColor
        appBarTextTitleColor = other.appBarTextTitleColor
        appBarTextSubTitleColor = other.appBarTextSubTitleColor
        sendButtonEnabled = other.sendButtonEnabled
        sendButtonDisabled = other.sendButtonDisabled
    }
}


@Composable
fun ProvideCalibreColors(
    colors: CalibreColorPalette,
    content: @Composable () -> Unit
) {
    var colorPalette by remember { mutableStateOf(colors) }
    colorPalette = (colors)
    CompositionLocalProvider(LocalCalibreColor provides colorPalette, content = content)
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