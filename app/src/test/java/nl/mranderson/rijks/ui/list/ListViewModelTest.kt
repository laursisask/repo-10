package nl.mranderson.rijks.ui.list

import TestCoroutineExtension
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import nl.mranderson.rijks.domain.model.Art
import nl.mranderson.rijks.domain.usecase.GetCollection
import nl.mranderson.rijks.ui.list.ListViewModel.ArtUIModel
import nl.mranderson.rijks.ui.list.ListViewModel.ArtUIModel.ArtData
import nl.mranderson.rijks.ui.list.ListViewModel.ArtUIModel.AuthorSeparator
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

@OptIn(ExperimentalCoroutinesApi::class)
@ExperimentalTime
class ListViewModelTest {

    @JvmField
    @RegisterExtension
    val coroutines = TestCoroutineExtension()

    private var getCollection = mockk<GetCollection>()

    private fun viewModel() = ListViewModel(
        getCollection = getCollection
    )

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `Given an art piece, When fetching collection, Then convert to list data`() = runTest {
        // Given
        val art = Art(
            objectNumber = "ID-001",
            title = "Nachtwacht",
            author = "Rembrandt",
            imageUrl = "www.mranderson.nl"
        )
        val differ = AsyncPagingDataDiffer(
            diffCallback = MyDiffCallback(),
            updateCallback = NoopListCallback(),
            workerDispatcher = Dispatchers.Main
        )
        coEvery { getCollection() } returns flowOf(PagingData.from(listOf(art)))

        // When
        val viewModel = viewModel()

        // Then
        viewModel.artCollectionFlow.test {
            differ.submitData(awaitItem())
            advanceUntilIdle()
            assertTrue(differ.snapshot().items[0] is AuthorSeparator)
            assertEquals("Rembrandt", (differ.snapshot().items[0] as AuthorSeparator).author)
            assertTrue(differ.snapshot().items[1] is ArtData)
            assertEquals("Rembrandt", (differ.snapshot().items[1] as ArtData).author)
            assertEquals("ID-001", (differ.snapshot().items[1] as ArtData).id)
            assertEquals("Nachtwacht", (differ.snapshot().items[1] as ArtData).title)
            assertEquals("www.mranderson.nl", (differ.snapshot().items[1] as ArtData).imageUrl)
        }
    }

    @Test
    fun `Given multiple art pieces, When fetching collection, Then set correct list items with separators`() =
        runTest {
            // Given
            val artPiece = Art(
                objectNumber = "ID-001",
                title = "Nachtwacht",
                author = "Rembrandt",
                imageUrl = "www.mranderson.nl"
            )
            val anotherArtPiece = Art(
                objectNumber = "ID-002",
                title = "Landschap",
                author = "Vermeer",
                imageUrl = "www.mranderson.nl"
            )
            val differ = AsyncPagingDataDiffer(
                diffCallback = MyDiffCallback(),
                updateCallback = NoopListCallback(),
                workerDispatcher = Dispatchers.Main
            )
            coEvery { getCollection() } returns
                    flowOf(
                        PagingData.from(
                            listOf(
                                artPiece,
                                anotherArtPiece
                            )
                        )
                    )


            // When
            val viewModel = viewModel()

            // Then
            viewModel.artCollectionFlow.test {
                differ.submitData(awaitItem())
                advanceUntilIdle()
                assertTrue(differ.snapshot().items[0] is AuthorSeparator)
                assertTrue(differ.snapshot().items[1] is ArtData)
                assertTrue(differ.snapshot().items[2] is AuthorSeparator)
                assertTrue(differ.snapshot().items[3] is ArtData)
            }
        }
}

class NoopListCallback : ListUpdateCallback {
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
}

class MyDiffCallback : DiffUtil.ItemCallback<ArtUIModel>() {
    override fun areItemsTheSame(oldItem: ArtUIModel, newItem: ArtUIModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ArtUIModel, newItem: ArtUIModel): Boolean {
        return oldItem == newItem
    }
}