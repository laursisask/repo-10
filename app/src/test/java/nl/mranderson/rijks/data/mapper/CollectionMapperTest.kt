package nl.mranderson.rijks.data.mapper

import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import nl.mranderson.rijks.data.model.ArtResponse
import nl.mranderson.rijks.data.model.CollectionResponse
import nl.mranderson.rijks.data.model.ImageResponse
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CollectionMapperTest {

    private val collectionMapper = CollectionMapper

    private var collectionResponse = mockk<CollectionResponse>()
    private var artResponse = mockk<ArtResponse>()
    private var imageResponse = mockk<ImageResponse>()

    @BeforeEach
    fun setUp() {
        every { collectionResponse.artObjects } returns listOf(artResponse)
        every { artResponse.objectNumber } returns ""
        every { artResponse.id } returns ""
        every { artResponse.title } returns ""
        every { artResponse.principalOrFirstMaker } returns ""
        every { artResponse.webImage } returns imageResponse
        every { imageResponse.url } returns ""
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `Given art title, When art detail is mapped, Then art title is correctly mapped`() {
        // Given
        val title = "Nachtwacht"
        every { artResponse.title } returns title

        // When
        val result = collectionMapper.map(collectionResponse).first().title

        // Then
        assertEquals(title, result)
    }

    @Test
    fun `Given art author, When art detail is mapped, Then art author is correctly mapped`() {
        // Given
        val author = "Rembrandt"
        every { artResponse.principalOrFirstMaker } returns author

        // When
        val result = collectionMapper.map(collectionResponse).first().author

        // Then
        assertEquals(author, result)
    }

    @Test
    fun `Given art objectNumber, When art detail is mapped, Then art objectNumber is correctly mapped`() {
        // Given
        val objectNumber = "ID-001"
        every { artResponse.objectNumber } returns objectNumber

        // When
        val result = collectionMapper.map(collectionResponse).first().objectNumber

        // Then
        assertEquals(objectNumber, result)
    }

    @Test
    fun `Given art image url, When art detail is mapped, Then art image url is correctly mapped`() {
        // Given
        val url = "www.mranderson.nl"
        every { imageResponse.url } returns url

        // When
        val result = collectionMapper.map(collectionResponse).first().imageUrl

        // Then
        assertEquals(url, result)
    }

}