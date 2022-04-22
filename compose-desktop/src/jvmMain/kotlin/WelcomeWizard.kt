import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import welcome.Language
import welcome.LanguageDropdownModel

@Composable
fun WelcomeWizard() {
    Column {
        WWHeadline()
        Divider()
        ChooseLanguage()
    }
}

@Composable
fun ChooseLanguage() {
    Row(
        Modifier.padding(24.dp),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center
    ) {
        Text("Choose your language: ")
        LanguageDropDown()
    }
}

@Composable
fun LanguageDropDown() {
    var isOpen by remember { mutableStateOf(false) }
    var languages by remember { mutableStateOf<LanguageDropdownModel?>(null) }
    var selectedIndex by remember { mutableStateOf(0) }

    LaunchedEffect(true) {
        withContext(Dispatchers.IO) {
            val langStream = javaClass.classLoader.getResourceAsStream("setup/iso_639-3.json")
            langStream?.let {
                languages = Gson().fromJson(it.reader(), LanguageDropdownModel::class.java)
                selectedIndex = languages?.languages?.indexOfFirst { it?.alpha_2 == "en" } ?: 0
                print(languages)
            }
        }
    }

    androidx.compose.foundation.layout.Box(
        modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.TopStart)
    ) {
        Button(onClick = {
            isOpen = true
        }) {
            Text(languages?.languages?.get(selectedIndex)?.name ?: "Not Selected")
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
