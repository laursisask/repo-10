import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageResponse(
    @SerialName("guid")
    val guid: String?,
    @SerialName("offsetPercentageX")
    val offsetPercentageX: Int,
    @SerialName("offsetPercentageY")
    val offsetPercentageY: Int,
    @SerialName("width")
    val width: Int,
    @SerialName("height")
    val height: Int,
    @SerialName("url")
    val url: String
)