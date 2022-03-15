package nl.mranderson.rijks.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import nl.mranderson.rijks.domain.model.Art

interface CollectionRepository {

    fun getCollection(): Flow<PagingData<Art>>

}