package nl.mranderson.rijks.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import nl.mranderson.rijks.domain.CollectionRepository
import nl.mranderson.rijks.domain.model.Art
import nl.mranderson.rijks.domain.model.ArtDetails

class CollectionRepositoryImpl @Inject constructor(
    private val collectionPagingSource: CollectionPagingSource,
    private val collectionRemoteDataSource: CollectionRemoteDataSource
) : CollectionRepository {

    companion object {
        const val NETWORK_PAGE_SIZE = 30
    }

    override fun getCollection(): Flow<PagingData<Art>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = { collectionPagingSource }
        ).flow
    }

    override suspend fun getArtDetails(artId: String): Result<ArtDetails> {
        return try {
            val response = collectionRemoteDataSource.getArtDetails(artId = artId)
            Result.success(response)
        } catch (exception: Exception) {
            Result.failure(exception = exception)
        }
    }
}

