package nl.mranderson.rijks.domain.usecase

import kotlinx.coroutines.test.runTest
import nl.mranderson.rijks.domain.CollectionRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetCollectionTest {

    private lateinit var getCollection: GetCollection

    private var collectionRepository: CollectionRepository = mock()

    @BeforeEach
    fun setUp() {
        getCollection = GetCollection(
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
    fun `Given a valid response, When retrieving the banks, Then it will return the banks`() =
        runTest {

            whenever(collectionRepository.getCollection()).thenReturn(mock())

        }
}