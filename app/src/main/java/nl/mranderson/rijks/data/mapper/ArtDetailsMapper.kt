package nl.mranderson.rijks.data.mapper

import nl.mranderson.rijks.data.model.ArtDetailResponse
import nl.mranderson.rijks.domain.model.ArtDetails

object ArtDetailsMapper : Mapper<ArtDetailResponse, ArtDetails> {

    override fun map(data: ArtDetailResponse) : ArtDetails = data.artObject.run {
            ArtDetails(
                objectNumber = this.objectNumber,
                title = this.title,
                description = this.description,
                types = this.objectTypes,
                author = this.principalOrFirstMaker,
                imageUrl = this.webImage.url
            )
    }
}