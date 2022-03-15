package nl.mranderson.rijks.data.mapper

import ArtDetailResponse
import ArtPieceDetailResponse
import ImageResponse
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ArtDetailsMapperTest {

    private val artDetailsMapper = ArtDetailsMapper

    private var artDetailResponse: ArtDetailResponse = mock()
    private var artPieceDetailResponse: ArtPieceDetailResponse = mock()
    private var imageResponse: ImageResponse = mock()

    @BeforeEach
    fun setUp() {
        whenever(artDetailResponse.artObject).thenReturn(artPieceDetailResponse)
        whenever(artPieceDetailResponse.objectNumber).thenReturn("")
        whenever(artPieceDetailResponse.id).thenReturn("")
        whenever(artPieceDetailResponse.title).thenReturn("")
        whenever(artPieceDetailResponse.principalOrFirstMaker).thenReturn("")
        whenever(artPieceDetailResponse.webImage).thenReturn(imageResponse)
        whenever(imageResponse.url).thenReturn("")
    }

    @AfterEach
    fun tearDown() {
        Mockito.reset(
            artDetailResponse,
            artPieceDetailResponse,
            imageResponse
        )
    }


    @Test
    fun `Given art title, When art detail is mapped, Then art title is correctly mapped`() {
        // Given
        val title = "Nachtwacht"
        whenever(artPieceDetailResponse.title).thenReturn(title)

        // When
        val result = artDetailsMapper.map(artDetailResponse).title

        // Then
        assertEquals(title, result)
    }

    @Test
    fun `Given art author, When art detail is mapped, Then art author is correctly mapped`() {
        // Given
        val author = "Rembrandt"
        whenever(artPieceDetailResponse.principalOrFirstMaker).thenReturn(author)

        // When
        val result = artDetailsMapper.map(artDetailResponse).author

        // Then
        assertEquals(author, result)
    }

    @Test
    fun `Given art objectNumber, When art detail is mapped, Then art objectNumber is correctly mapped`() {
        // Given
        val objectNumber = "ID-001"
        whenever(artPieceDetailResponse.objectNumber).thenReturn(objectNumber)

        // When
        val result = artDetailsMapper.map(artDetailResponse).objectNumber

        // Then
        assertEquals(objectNumber, result)
    }

    @Test
    fun `Given art image url, When art detail is mapped, Then art image url is correctly mapped`() {
        // Given
        val url = "www.mranderson.nl"
        whenever(imageResponse.url).thenReturn(url)

        // When
        val result = artDetailsMapper.map(artDetailResponse).imageUrl

        // Then
        assertEquals(url, result)
    }

    @Test
    fun `Given art description is empty, When art detail is mapped, Then art description is correctly mapped`() {
        // Given
        whenever(artPieceDetailResponse.description).thenReturn(null)

        // When
        val result = artDetailsMapper.map(artDetailResponse).description

        // Then
        assertNull(result)
    }

    @Test
    fun `Given art description, When art detail is mapped, Then art description is correctly mapped`() {
        // Given
        val description = "Testing"
        whenever(artPieceDetailResponse.description).thenReturn(description)

        // When
        val result = artDetailsMapper.map(artDetailResponse).description

        // Then
        assertEquals(description, result)
    }

    @Test
    fun `Given art object types, When art detail is mapped, Then art object types is correctly mapped`() {
        // Given
        val types = listOf("Testing", "Painting")
        whenever(artPieceDetailResponse.objectTypes).thenReturn(types)

        // When
        val result = artDetailsMapper.map(artDetailResponse).types

        // Then
        assertEquals(types, result)
    }
}