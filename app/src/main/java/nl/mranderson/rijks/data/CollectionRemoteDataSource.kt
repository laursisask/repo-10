package nl.mranderson.rijks.data

import nl.mranderson.rijks.data.api.CollectionApiService
import nl.mranderson.rijks.data.mapper.CollectionMapper
import nl.mranderson.rijks.domain.model.Art

class CollectionRemoteDataSource(
    private val collectionApiService: CollectionApiService,
    private val collectionMapper: CollectionMapper
) : CollectionDataSource {

    override suspend fun getCollection(page: Int, loadSize : Int): List<Art> {
        val response = collectionApiService.getCollection(page, loadSize)
        return collectionMapper.map(response)
    }

}