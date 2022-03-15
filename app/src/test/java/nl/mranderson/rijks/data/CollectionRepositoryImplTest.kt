package nl.mranderson.rijks.data

import kotlinx.coroutines.test.runTest
import nl.mranderson.rijks.domain.model.ArtDetails
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class CollectionRepositoryImplTest {

    private lateinit var collectionRepository: CollectionRepositoryImpl

    private var collectionPagingSource: CollectionPagingSource = mock()
    private var collectionRemoteDataSource: CollectionRemoteDataSource = mock()

    @BeforeEach
    fun setUp() {
        collectionRepository = CollectionRepositoryImpl(
            collectionPagingSource = collectionPagingSource,
            collectionRemoteDataSource = collectionRemoteDataSource
        )
    }

    @AfterEach
    fun tearDown() {
        Mockito.reset(
            collectionPagingSource,
            collectionRemoteDataSource
        )
    }


    @Test
    fun `Given an id, When retrieving the details of an art piece, Then return details object`() =
        runTest {
            // Given
            val artId = "ID-001"
            val artDetails: ArtDetails = mock()
            whenever(collectionRemoteDataSource.getArtDetails(any())).thenReturn(
                artDetails
            )

            // When
            val result = collectionRepository.getArtDetails(artId)

            // Then
            assertTrue(result.isSuccess)
            assertEquals(artDetails, result.getOrNull())
        }

    @Test
    fun `Given an error, When retrieving the details of an art piece, Then return`() =
        runTest {
            // Given
            val artId = "ID-001"
            whenever(collectionRemoteDataSource.getArtDetails(any())).thenThrow(RuntimeException())

            // When
            val result = collectionRepository.getArtDetails(artId)

            // Then
            assertTrue(result.isFailure)
        }

}