package nl.mranderson.rijks.data.mapper

import ArtResponse
import CollectionResponse
import ImageResponse
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class CollectionMapperTest {

    private val collectionMapper = CollectionMapper

    private var collectionResponse: CollectionResponse = mock()
    private var artResponse: ArtResponse = mock()
    private var imageResponse: ImageResponse = mock()

    @BeforeEach
    fun setUp() {
        whenever(collectionResponse.artObjects).thenReturn(listOf(artResponse))
        whenever(artResponse.objectNumber).thenReturn("")
        whenever(artResponse.id).thenReturn("")
        whenever(artResponse.title).thenReturn("")
        whenever(artResponse.principalOrFirstMaker).thenReturn("")
        whenever(artResponse.webImage).thenReturn(imageResponse)
        whenever(imageResponse.url).thenReturn("")
    }

    @AfterEach
    fun tearDown() {
        Mockito.reset(
            collectionResponse,
            artResponse,
            imageResponse
        )
    }

    @Test
    fun `Given art title, When art detail is mapped, Then art title is correctly mapped`() {
        // Given
        val title = "Nachtwacht"
        whenever(artResponse.title).thenReturn(title)

        // When
        val result = collectionMapper.map(collectionResponse).first().title

        // Then
        assertEquals(title, result)
    }

    @Test
    fun `Given art author, When art detail is mapped, Then art author is correctly mapped`() {
        // Given
        val author = "Rembrandt"
        whenever(artResponse.principalOrFirstMaker).thenReturn(author)

        // When
        val result = collectionMapper.map(collectionResponse).first().author

        // Then
        assertEquals(author, result)
    }

    @Test
    fun `Given art objectNumber, When art detail is mapped, Then art objectNumber is correctly mapped`() {
        // Given
        val objectNumber = "ID-001"
        whenever(artResponse.objectNumber).thenReturn(objectNumber)

        // When
        val result = collectionMapper.map(collectionResponse).first().objectNumber

        // Then
        assertEquals(objectNumber, result)
    }

    @Test
    fun `Given art image url, When art detail is mapped, Then art image url is correctly mapped`() {
        // Given
        val url = "www.mranderson.nl"
        whenever(imageResponse.url).thenReturn(url)

        // When
        val result = collectionMapper.map(collectionResponse).first().imageUrl

        // Then
        assertEquals(url, result)
    }

}