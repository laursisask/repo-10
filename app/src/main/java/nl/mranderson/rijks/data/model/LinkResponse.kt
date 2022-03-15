import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LinkResponse (
	@SerialName("self")
	val self : String,
	@SerialName("web")
	val web : String
)