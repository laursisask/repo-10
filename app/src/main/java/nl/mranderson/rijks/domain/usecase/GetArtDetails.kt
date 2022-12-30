package nl.mranderson.rijks.domain.usecase

import javax.inject.Inject
import nl.mranderson.rijks.domain.CollectionRepository

class GetArtDetails @Inject constructor(
    private val collectionRepository: CollectionRepository
) {

    suspend operator fun invoke(artId: String) = collectionRepository.getArtDetails(artId = artId)

}