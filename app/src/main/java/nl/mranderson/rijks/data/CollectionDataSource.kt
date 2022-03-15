package nl.mranderson.rijks.data

import nl.mranderson.rijks.domain.model.ArtDetails

interface CollectionDataSource {

    suspend fun getArtDetails(artId: String): ArtDetails

}