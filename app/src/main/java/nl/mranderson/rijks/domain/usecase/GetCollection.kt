package nl.mranderson.rijks.domain.usecase

import javax.inject.Inject
import nl.mranderson.rijks.domain.CollectionRepository

class GetCollection @Inject constructor(
    private val collectionRepository: CollectionRepository
) {

    operator fun invoke() = collectionRepository.getCollection()

}