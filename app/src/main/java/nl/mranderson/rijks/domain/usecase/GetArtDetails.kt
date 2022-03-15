package nl.mranderson.rijks.domain.usecase

import nl.mranderson.rijks.domain.CollectionRepository

class GetArtDetails(
    private val collectionRepository: CollectionRepository
) {

    suspend operator fun invoke(artId: String) = collectionRepository.getArtDetails(artId = artId)

}