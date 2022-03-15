package nl.mranderson.rijks.domain.usecase

import nl.mranderson.rijks.domain.CollectionRepository

class GetCollection(
    private val collectionRepository: CollectionRepository
) {

    operator fun invoke() = collectionRepository.getCollection()

}