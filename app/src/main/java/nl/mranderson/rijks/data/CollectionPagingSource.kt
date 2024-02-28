package nl.mranderson.rijks.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import javax.inject.Inject
import nl.mranderson.rijks.data.CollectionRepositoryImpl.Companion.NETWORK_PAGE_SIZE
import nl.mranderson.rijks.data.api.CollectionApiService
import nl.mranderson.rijks.data.mapper.CollectionMapper
import nl.mranderson.rijks.domain.model.Art

private const val RIJKS_STARTING_PAGE_INDEX = 1

class CollectionPagingSource @Inject constructor(
    private val collectionApiService: CollectionApiService,
    private val collectionMapper: CollectionMapper
) : PagingSource<Int, Art>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Art> {
        val position = params.key ?: RIJKS_STARTING_PAGE_INDEX
        return try {
            val collection = collectionApiService.getCollection(position, params.loadSize)
            val list = collectionMapper.map(collection)

            val nextKey = if (list.isEmpty()) {
                null
            } else {
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = list,
                prevKey = null, //Only paging forward
                nextKey = nextKey
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Art>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
