import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtResponse(
    @SerialName("links")
    val link: LinkResponse,
    @SerialName("id")
    val id: String,
    @SerialName("objectNumber")
    val objectNumber: String,
    @SerialName("title")
    val title: String,
    @SerialName("hasImage")
    val hasImage: Boolean,
    @SerialName("principalOrFirstMaker")
    val principalOrFirstMaker: String,
    @SerialName("longTitle")
    val longTitle: String,
    @SerialName("showImage")
    val showImage: Boolean,
    @SerialName("permitDownload")
    val permitDownload: Boolean,
    @SerialName("webImage")
    val webImage: ImageResponse,
    @SerialName("headerImage")
    val headerImage: ImageResponse,
    @SerialName("productionPlaces")
    val productionPlaces: List<String>
)