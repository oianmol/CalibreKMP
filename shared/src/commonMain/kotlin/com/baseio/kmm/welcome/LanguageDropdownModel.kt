package com.baseio.kmm.welcome

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class LanguageDropdownModel(
    @SerialName("639-3")
    var languages: List<Language>
)

@kotlinx.serialization.Serializable
data class Language(
    val alpha_2: String? = null,
    val alpha_3: String? = null,
    val bibliographic: String? = null,
    val common_name: String? = null,
    val inverted_name: String? = null,
    val name: String? = null,
    val scope: String? = null,
    val type: String? = null
)