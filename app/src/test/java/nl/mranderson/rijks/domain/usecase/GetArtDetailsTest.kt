package nl.mranderson.rijks.domain.usecase

import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import nl.mranderson.rijks.domain.CollectionRepository
import nl.mranderson.rijks.domain.model.ArtDetails
import org.junit.Test
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach

@OptIn(ExperimentalCoroutinesApi::class)
class GetArtDetailsTest {

    private lateinit var getArtDetails: GetArtDetails

    private var collectionRepository = mockk<CollectionRepository>()

    @BeforeEach
    fun setUp() {
        getArtDetails = GetArtDetails(
            collectionRepository = collectionRepository
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
            val artDetails: ArtDetails = mockk()
            coEvery { collectionRepository.getArtDetails(any()) } returns Result.success(artDetails)

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
            coEvery { collectionRepository.getArtDetails(any()) } returns Result.failure(Throwable())

            // When
            val result = getArtDetails(artId)

            // Then
            assertTrue(result.isFailure)
        }
}