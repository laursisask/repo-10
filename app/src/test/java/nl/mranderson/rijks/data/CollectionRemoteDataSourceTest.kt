package nl.mranderson.rijks.data

import ArtDetailResponse
import kotlinx.coroutines.test.runTest
import nl.mranderson.rijks.data.api.CollectionApiService
import nl.mranderson.rijks.data.mapper.ArtDetailsMapper
import nl.mranderson.rijks.domain.model.ArtDetails
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class CollectionRemoteDataSourceTest {

    private lateinit var collectionRemoteDataSource: CollectionRemoteDataSource

    private var collectionApiService: CollectionApiService = mock()
    private var artDetailsMapper: ArtDetailsMapper = mock()

    @BeforeEach
    fun setUp() {
        collectionRemoteDataSource = CollectionRemoteDataSource(
            collectionApiService = collectionApiService,
            artDetailsMapper = artDetailsMapper
        )
    }

    @AfterEach
    fun tearDown() {
        Mockito.reset(
            collectionApiService,
            artDetailsMapper
        )
    }

    @Test
    fun `Given an id, When retrieving the details of an art piece, Then return details object`() =
        runTest {
            // Given
            val artId = "ID-001"
            val artDetailResponse: ArtDetailResponse = mock()
            val artDetails: ArtDetails = mock()
            whenever(collectionApiService.getArtDetails(any())).thenReturn(artDetailResponse)
            whenever(artDetailsMapper.map(any())).thenReturn(artDetails)

            // When
            val result = collectionRemoteDataSource.getArtDetails(artId)

            // Then
            assertEquals(artDetails, result)
        }
}