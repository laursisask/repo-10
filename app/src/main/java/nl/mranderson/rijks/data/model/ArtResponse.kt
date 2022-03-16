package nl.mranderson.rijks.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtResponse(
    @SerialName("id")
    val id: String,
    @SerialName("objectNumber")
    val objectNumber: String,
    @SerialName("title")
    val title: String,
    @SerialName("principalOrFirstMaker")
    val principalOrFirstMaker: String,
    @SerialName("webImage")
    val webImage: ImageResponse
)