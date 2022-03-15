package nl.mranderson.rijks.domain.model

data class ArtDetails(
    val objectNumber: String,
    val title: String,
    val description : String?,
    val types : List<String>,
    val author: String,
    val imageUrl : String
)

