package nl.mranderson.rijks.ui.detail

import TestCoroutineExtension
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import nl.mranderson.rijks.InstantExecutorExtension
import nl.mranderson.rijks.domain.model.ArtDetails
import nl.mranderson.rijks.domain.usecase.GetArtDetails
import nl.mranderson.rijks.ui.detail.DetailViewModel.ScreenState
import nl.mranderson.rijks.ui.detail.DetailViewModel.ScreenState.Data
import nl.mranderson.rijks.ui.detail.DetailViewModel.ScreenState.Error
import nl.mranderson.rijks.ui.detail.DetailViewModel.ScreenState.Loading
import nl.mranderson.rijks.ui.navigation.Screens
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(InstantExecutorExtension::class)
class DetailViewModelTest {

    @JvmField
    @RegisterExtension
    val coroutines = TestCoroutineExtension()

    private var getArtDetails = mockk<GetArtDetails>()
    private var savedStateHandle = mockk<SavedStateHandle>()
    private val stateCallback = mockk<Observer<ScreenState>>()

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
    fun `When initializing viewmodel, Then state should be loading`() = runTest {
        // Given
        every { stateCallback.onChanged(any()) } answers { Loading }

        // When
        val viewModel = viewModel()
        viewModel.state.observeForever(stateCallback)

        // Then
        verify { stateCallback.onChanged(Loading) }
    }

    @Test
    fun `Given fetching details is success, When initializing viewmodel, Then state should be returning data`() =
        runTest {
            // Given
            val artDetails = mockk<ArtDetails>()
            coEvery { getArtDetails(any()) } returns Result.success(artDetails)
            every { stateCallback.onChanged(any()) } answers { Data(artDetails) }

            // When
            val viewModel = viewModel()
            viewModel.state.observeForever(stateCallback)

            // Then
            verify { stateCallback.onChanged(Data(artDetails)) }
        }

    @Test
    fun `Given fetching details is failure, When initializing viewmodel, Then state should be error`() =
        runTest {
            // Given
            coEvery { getArtDetails(any()) } returns Result.failure(RuntimeException())
            every { stateCallback.onChanged(any()) } answers { Error }

            // When
            val viewModel = viewModel()
            viewModel.state.observeForever(stateCallback)

            // Then
            verify { stateCallback.onChanged(Error) }
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
            every { stateCallback.onChanged(any()) } answers { Data(artDetails) }

            // When
            val viewModel = viewModel()
            viewModel.onRetryClicked()
            viewModel.state.observeForever(stateCallback)

            // Then
            verify { stateCallback.onChanged(Data(artDetails)) }
        }
}