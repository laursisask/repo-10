package nl.mranderson.rijks.data

import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import nl.mranderson.rijks.domain.model.ArtDetails
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
@ExperimentalTime
class CollectionRepositoryImplTest {

    private lateinit var collectionRepository: CollectionRepositoryImpl

    private var collectionPagingSource = mockk<CollectionPagingSource>()
    private var collectionRemoteDataSource = mockk<CollectionRemoteDataSource>()

    @BeforeEach
    fun setUp() {
        collectionRepository = CollectionRepositoryImpl(
            collectionPagingSource = collectionPagingSource,
            collectionRemoteDataSource = collectionRemoteDataSource
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
            val artDetails = mockk<ArtDetails>()
            coEvery { collectionRemoteDataSource.getArtDetails(any()) } returns artDetails

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
            coEvery { collectionRemoteDataSource.getArtDetails(any()) } throws RuntimeException()

            // When
            val result = collectionRepository.getArtDetails(artId)

            // Then
            assertTrue(result.isFailure)
        }
}

