package nl.mranderson.rijks.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtPieceDetailResponse(
    @SerialName("id")
    val id: String,
    @SerialName("objectNumber")
    val objectNumber: String,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String?,
    @SerialName("objectTypes")
    val objectTypes: List<String>,
    @SerialName("principalOrFirstMaker")
    val principalOrFirstMaker: String,
    @SerialName("webImage")
    val webImage: ImageResponse,
)