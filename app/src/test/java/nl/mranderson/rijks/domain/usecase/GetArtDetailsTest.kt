package nl.mranderson.rijks.domain.usecase

import kotlinx.coroutines.test.runTest
import net.bytebuddy.implementation.bytecode.Throw
import nl.mranderson.rijks.domain.CollectionRepository
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

class GetArtDetailsTest {

    private lateinit var getArtDetails: GetArtDetails

    private var collectionRepository: CollectionRepository = mock()

    @BeforeEach
    fun setUp() {
        getArtDetails = GetArtDetails(
            collectionRepository = collectionRepository
        )
    }

    @AfterEach
    fun tearDown() {
        Mockito.reset(
            collectionRepository
        )
    }

    @Test
    fun `Given an id, When retrieving the details of an art piece, Then return details object`() =
        runTest {
            // Given
            val artId = "ID-001"
            val artDetails: ArtDetails = mock()
            whenever(collectionRepository.getArtDetails(any())).thenReturn(
                Result.success(artDetails)
            )

            // When
            val result = getArtDetails(artId)

            // Then
            assertTrue(result.isSuccess)
            assertEquals(artDetails, result.getOrNull())
        }

    @Test
    fun `Given an error, When retrieving the details of an art piece, Then return`() =
        runTest {
            // Given
            val artId = "ID-001"
            whenever(collectionRepository.getArtDetails(any())).thenReturn(
                Result.failure(Throwable())
            )

            // When
            val result = getArtDetails(artId)

            // Then
            assertTrue(result.isFailure)
        }
}