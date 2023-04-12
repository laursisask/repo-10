package nl.mranderson.rijks.ui.detail

import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.test.runTest
import nl.mranderson.rijks.InstantExecutorExtension
import nl.mranderson.rijks.TestCoroutineExtension
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
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExtendWith(InstantExecutorExtension::class)
class DetailViewModelTest {

    @JvmField
    @RegisterExtension
    val coroutines = TestCoroutineExtension()

    private var getArtDetails: GetArtDetails = mock()
    private var savedStateHandle: SavedStateHandle = mock()
    private val stateCallback: Observer<ScreenState> = mock()

    private fun viewModel() = DetailViewModel(
        savedState = savedStateHandle,
        getArtDetails = getArtDetails
    )

    @BeforeEach
    fun setUp() {
        whenever(savedStateHandle.get<String>(Screens.Detail.argArtId)).thenReturn("AB-001")
    }

    @AfterEach
    fun tearDown() {
        Mockito.reset(
            getArtDetails
        )
    }

    @Test
    fun `When initializing viewmodel, Then state should be loading`() = runTest {
        // When
        val viewModel = viewModel()
        viewModel.state.observeForever(stateCallback)

        // Then
        verify(stateCallback).onChanged(Loading)
    }

    @Test
    fun `Given fetching details is success, When initializing viewmodel, Then state should be returning data`() =
        runTest {
            // Given
            val artDetails: ArtDetails = mock()
            whenever(getArtDetails(any())).thenReturn(Result.success(artDetails))

            // When
            val viewModel = viewModel()
            viewModel.state.observeForever(stateCallback)

            // Then
            verify(stateCallback).onChanged(Data(artDetails))
        }

    @Test
    fun `Given fetching details is failure, When initializing viewmodel, Then state should be error`() =
        runTest {
            // Given
            whenever(getArtDetails(any())).thenReturn(Result.failure(RuntimeException()))

            // When
            val viewModel = viewModel()
            viewModel.state.observeForever(stateCallback)

            // Then
            verify(stateCallback).onChanged(Error)
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
            whenever(getArtDetails(any())).thenReturn(Result.success(artDetails))

            // When
            val viewModel = viewModel()
            viewModel.onRetryClicked()
            viewModel.state.observeForever(stateCallback)

            // Then
            verify(stateCallback).onChanged(Data(artDetails))
        }
}