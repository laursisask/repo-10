package nl.mranderson.rijks.data.mapper

import CollectionResponse
import nl.mranderson.rijks.domain.model.Art

object CollectionMapper : Mapper<CollectionResponse, List<Art>> {

    override fun map(data: CollectionResponse) = data.run {
        artObjects.map {
            Art(
                objectNumber = it.objectNumber,
                title = it.title,
                author = it.principalOrFirstMaker,
                imageUrl = it.webImage.url
            )
        }
    }
}

