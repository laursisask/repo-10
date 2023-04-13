package nl.mranderson.rijks.ui.detail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import nl.mranderson.rijks.TestCoroutineExtension
import nl.mranderson.rijks.domain.model.ArtDetails
import nl.mranderson.rijks.domain.usecase.GetArtDetails
import nl.mranderson.rijks.ui.detail.DetailViewModel.ScreenState.Data
import nl.mranderson.rijks.ui.detail.DetailViewModel.ScreenState.Error
import nl.mranderson.rijks.ui.navigation.Screens
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {

    @JvmField
    @RegisterExtension
    val coroutines = TestCoroutineExtension()

    private var getArtDetails = mockk<GetArtDetails>()
    private var savedStateHandle = mockk<SavedStateHandle>()

    private fun viewModel() = DetailViewModel(
        savedState = savedStateHandle,
        getArtDetails = getArtDetails
    )

    @BeforeEach
    fun setUp() {
        every { savedStateHandle.get<String>(Screens.Detail.argArtId) } returns "AB-001"
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `Given fetching details is success, When initializing viewmodel, Then state should be returning data`() =
        runTest {
            // Given
            val artDetails = mockk<ArtDetails>()
            coEvery { getArtDetails(any()) } returns Result.success(artDetails)

            // When
            val viewModel = viewModel()

            // Then
            viewModel.state.test {
                assertEquals(awaitItem(), Data(artDetails))
            }
        }

    @Test
    fun `Given fetching details is failure, When initializing viewmodel, Then state should be error`() =
        runTest {
            // Given
            coEvery { getArtDetails(any()) } returns Result.failure(RuntimeException())

            // When
            val viewModel = viewModel()

            // Then
            viewModel.state.test {
                assertEquals(awaitItem(), Error)
            }
        }

    @Test
    fun `Given fetching details is success, When retrying, Then state should be returning data`() =
        runTest {
            // Given
            val artDetails = ArtDetails(
                objectNumber = "",
                title = "",
                description = "",
                types = listOf(),
                author = "",
                imageUrl = ""
            )
            coEvery { getArtDetails(any()) } returns Result.success(artDetails)

            // When
            val viewModel = viewModel()
            viewModel.onRetryClicked()

            // Then
            viewModel.state.test {
                assertEquals(awaitItem(), Data(artDetails))
            }
        }
}