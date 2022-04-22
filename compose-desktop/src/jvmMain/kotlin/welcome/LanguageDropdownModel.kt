package welcome

import com.google.gson.annotations.SerializedName

data class LanguageDropdownModel(
    @SerializedName("639-3")
    var languages: List<Language>
)

data class Language(
    val alpha_2: String,
    val alpha_3: String,
    val bibliographic: String,
    val common_name: String,
    val inverted_name: String,
    val name: String,
    val scope: String,
    val type: String
)