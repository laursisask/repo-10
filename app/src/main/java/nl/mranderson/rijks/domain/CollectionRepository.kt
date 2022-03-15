package nl.mranderson.rijks.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import nl.mranderson.rijks.domain.model.Art
import nl.mranderson.rijks.domain.model.ArtDetails

interface CollectionRepository {

    fun getCollection(): Flow<PagingData<Art>>

    suspend fun getArtDetails(artId: String): Result<ArtDetails>

}