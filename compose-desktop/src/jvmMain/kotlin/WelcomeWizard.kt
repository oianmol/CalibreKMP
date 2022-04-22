import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import welcome.Language
import welcome.LanguageDropdownModel
import java.awt.FileDialog
import java.io.File


@Composable
fun WelcomeWizard(window: ComposeWindow) {
    Column {
        WWHeadline()
        Divider()
        LanguageDropDown()
        Spacer(Modifier.height(48.dp))
        FileLocationChooser(window)
    }
}

@Composable
fun FileLocationChooser(window: ComposeWindow) {

    Column(Modifier.padding(24.dp)) {
        Text("Choose a location for your books. When you add books to calibre, they will be copied here. Use an empty folder for a new calibre library:")
        FolderChooserButtons(window)
        Text("If a calibre library already exists at the newly selected location, calibre will use it automatically")
    }
}

@Composable
private fun FolderChooserButtons(
    window: ComposeWindow
) {
    val currentUsersHomeDir = System.getProperty("user.home")
    var otherFolder by remember { mutableStateOf(currentUsersHomeDir + File.separator.toString() + "Calibre Library") }

    Row {
        Button(
            onClick = {

            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            modifier = Modifier.fillMaxWidth(0.6f)
        ) {
            Text(otherFolder)
        }

        Spacer(Modifier.width(24.dp))

        Button(onClick = {
            val fileDialog = FileDialog(window)
            fileDialog.isMultipleMode = false
            fileDialog.mode = FileDialog.LOAD
            fileDialog.isVisible = true
            otherFolder = fileDialog.files.firstOrNull()?.absolutePath ?: ""
        }, modifier = Modifier.fillMaxWidth(0.4f)) {
            Text("Change")
        }
    }
}

@Composable
fun LanguageDropDown() {
    var isOpen by remember { mutableStateOf(false) }
    var languages by remember { mutableStateOf<LanguageDropdownModel?>(null) }
    var selectedIndex by remember { mutableStateOf(0) }

    LaunchedEffect(true) {
        withContext(Dispatchers.IO) {
            val langStream = javaClass.classLoader?.getResourceAsStream("setup/iso_639-3.json")
            langStream?.let {
                languages = Gson().fromJson(it.reader(), LanguageDropdownModel::class.java)
                selectedIndex = languages?.languages?.indexOfFirst { it.alpha_2 == "en" } ?: 0
            }
        }
    }

    androidx.compose.foundation.layout.Box(
        modifier = Modifier.padding(24.dp)
    ) {
        Row() {
            Text("Choose your language: ", Modifier.align(Alignment.CenterVertically))
            Button(onClick = {
                isOpen = true
            }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)) {
                Text(languages?.languages?.get(selectedIndex)?.name ?: "Not Selected")
            }
        }

        if (isOpen) {
            LazyColumn {
                itemsIndexed(languages?.languages ?: emptyList()) { index: Int, it: Language ->
                    DropdownMenuItem(onClick = {
                        selectedIndex = index
                        isOpen = false
                    }, modifier = Modifier.background(Color.White)) {
                        Text(it.name)
                    }
                }
            }
        }

    }

}

@Composable
fun WWHeadline() {
    ListItem(
        Modifier.padding(4.dp),
        secondaryText = { Text("The one stop solution to all your e-book needs.") },
        text = { Text("Welcome to CalibreKMM") }, trailing = {
            Image(
                painter = painterResource("images/library.png"),
                contentDescription = null,
                Modifier.size(64.dp)
            )
        })

}
