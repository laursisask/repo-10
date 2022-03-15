package nl.mranderson.rijks.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import nl.mranderson.rijks.domain.CollectionRepository
import nl.mranderson.rijks.domain.model.Art

class CollectionRepositoryImpl(
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

//    override suspend fun getArtDetails(artId : Int): Result<Art> {
//        return try {
//            val response = collectionRemoteDataSource.getCollection(page, loadSize)
//            Result.success(response)
//        } catch (exception: Exception) {
//            Result.failure(exception = exception)
//        }
//    }
}

