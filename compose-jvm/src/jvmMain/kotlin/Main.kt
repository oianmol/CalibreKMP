import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.baseio.kmm.di.initSharedDependencies
import theme.CalibreTheme

@Composable
@Preview
fun App(window: ComposeWindow) {
    CalibreTheme {
        WelcomeWizard(window)
    }
}

fun main() = application {
    initSharedDependencies()
    Window(onCloseRequest = ::exitApplication, title = "CalibreKMM Welcome wizard") {
        App(window)
    }
}
