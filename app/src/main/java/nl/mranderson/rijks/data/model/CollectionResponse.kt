import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CollectionResponse (
	@SerialName("count")
	val count : Int,
	@SerialName("artObjects")
	val artObjects : List<ArtResponse>
)