package nl.mranderson.rijks.data.mapper

import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import nl.mranderson.rijks.data.model.ArtDetailResponse
import nl.mranderson.rijks.data.model.ArtPieceDetailResponse
import nl.mranderson.rijks.data.model.ImageResponse
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ArtDetailsMapperTest {

    private val artDetailsMapper = ArtDetailsMapper

    private var artDetailResponse = mockk<ArtDetailResponse>()
    private var artPieceDetailResponse = mockk<ArtPieceDetailResponse>()
    private var imageResponse = mockk<ImageResponse>()

    @BeforeEach
    fun setUp() {
        every { artDetailResponse.artObject } returns artPieceDetailResponse
        every { artPieceDetailResponse.objectNumber } returns ""
        every { artPieceDetailResponse.id } returns ""
        every { artPieceDetailResponse.title } returns ""
        every { artPieceDetailResponse.principalOrFirstMaker } returns ""
        every { artPieceDetailResponse.webImage } returns imageResponse
        every { artPieceDetailResponse.objectTypes } returns emptyList()
        every { artPieceDetailResponse.description } returns ""
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
        every { artPieceDetailResponse.title } returns title

        // When
        val result = artDetailsMapper.map(artDetailResponse).title

        // Then
        assertEquals(title, result)
    }

    @Test
    fun `Given art author, When art detail is mapped, Then art author is correctly mapped`() {
        // Given
        val author = "Rembrandt"
        every { artPieceDetailResponse.principalOrFirstMaker } returns author

        // When
        val result = artDetailsMapper.map(artDetailResponse).author

        // Then
        assertEquals(author, result)
    }

    @Test
    fun `Given art objectNumber, When art detail is mapped, Then art objectNumber is correctly mapped`() {
        // Given
        val objectNumber = "ID-001"
        every { artPieceDetailResponse.objectNumber } returns objectNumber

        // When
        val result = artDetailsMapper.map(artDetailResponse).objectNumber

        // Then
        assertEquals(objectNumber, result)
    }

    @Test
    fun `Given art image url, When art detail is mapped, Then art image url is correctly mapped`() {
        // Given
        val url = "www.mranderson.nl"
        every { imageResponse.url } returns url

        // When
        val result = artDetailsMapper.map(artDetailResponse).imageUrl

        // Then
        assertEquals(url, result)
    }

    @Test
    fun `Given art description is empty, When art detail is mapped, Then art description is correctly mapped`() {
        // Given
        every { artPieceDetailResponse.description } returns null

        // When
        val result = artDetailsMapper.map(artDetailResponse).description

        // Then
        assertNull(result)
    }

    @Test
    fun `Given art description, When art detail is mapped, Then art description is correctly mapped`() {
        // Given
        val description = "Testing"
        every { artPieceDetailResponse.description } returns description

        // When
        val result = artDetailsMapper.map(artDetailResponse).description

        // Then
        assertEquals(description, result)
    }

    @Test
    fun `Given art object types, When art detail is mapped, Then art object types is correctly mapped`() {
        // Given
        val types = listOf("Testing", "Painting")
        every { artPieceDetailResponse.objectTypes } returns types

        // When
        val result = artDetailsMapper.map(artDetailResponse).types

        // Then
        assertEquals(types, result)
    }
}