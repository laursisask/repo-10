package nl.mranderson.rijks.domain.usecase

import androidx.paging.PagingData
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runTest
import nl.mranderson.rijks.domain.CollectionRepository
import nl.mranderson.rijks.domain.model.Art
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetCollectionTest {

    private lateinit var getCollection: GetCollection

    private var collectionRepository = mockk<CollectionRepository>()

    @BeforeEach
    fun setUp() {
        getCollection = GetCollection(
            collectionRepository = collectionRepository
        )
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `When retrieving the collection, Then return paging data`() =
        runTest {
            // Given
            val pagingData = mockk<Flow<PagingData<Art>>>()
            coEvery { collectionRepository.getCollection() } returns pagingData

            // When
            val result = getCollection()

            // Then
            assertEquals(pagingData, result)
        }
}