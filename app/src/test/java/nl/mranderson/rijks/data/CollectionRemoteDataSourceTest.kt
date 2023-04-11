package nl.mranderson.rijks.data

import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import nl.mranderson.rijks.data.api.CollectionApiService
import nl.mranderson.rijks.data.mapper.ArtDetailsMapper
import nl.mranderson.rijks.data.model.ArtDetailResponse
import nl.mranderson.rijks.domain.model.ArtDetails
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CollectionRemoteDataSourceTest {

    private lateinit var collectionRemoteDataSource: CollectionRemoteDataSource

    private var collectionApiService = mockk<CollectionApiService>()
    private var artDetailsMapper = mockk<ArtDetailsMapper>()

    @BeforeEach
    fun setUp() {
        collectionRemoteDataSource = CollectionRemoteDataSource(
            collectionApiService = collectionApiService,
            artDetailsMapper = artDetailsMapper
        )
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `Given an id, When retrieving the details of an art piece, Then return details object`() =
        runTest {
            // Given
            val artId = "ID-001"
            val artDetailResponse = mockk<ArtDetailResponse>()
            val artDetails = mockk<ArtDetails>()
            coEvery { collectionApiService.getArtDetails(any()) } returns artDetailResponse
            coEvery { artDetailsMapper.map(any()) } returns artDetails

            // When
            val result = collectionRemoteDataSource.getArtDetails(artId)

            // Then
            assertEquals(artDetails, result)
        }
}