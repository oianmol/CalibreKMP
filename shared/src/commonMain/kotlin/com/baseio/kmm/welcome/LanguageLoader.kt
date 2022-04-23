package com.baseio.kmm.welcome

import com.baseio.kmm.resources.resourceLoader
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object LanguageLoader {
    fun loadLanguagePack(): LanguageDropdownModel {
        val bytes = resourceLoader("setup/iso_639-3.json")
        val languageString = String(bytes)
        return Json.decodeFromString(languageString)
    }
}