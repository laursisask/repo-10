package nl.mranderson.rijks

import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.flowOf
import nl.mranderson.rijks.ui.MainActivity
import nl.mranderson.rijks.ui.list.ListScreen
import nl.mranderson.rijks.ui.list.ListViewModel
import nl.mranderson.rijks.ui.theme.RijksTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ListScreenTest {

    @Rule
    @JvmField
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val separator = ListViewModel.ArtUIModel.AuthorSeparator(
        author = "Rembrandt",
    )
    private val artPiece = ListViewModel.ArtUIModel.ArtData(
        id = "ID-002",
        title = "Landschap",
        author = "Vermeer",
        imageUrl = "www.mranderson.nl"
    )

    @Test
    fun checkIfListIsDisplayed() {
        composeTestRule.activity.setContent {
            RijksTheme {
                Surface {
                    ListScreen(
                        onArtClicked = {},
                        artCollection = flowOf(
                            PagingData.from(
                                listOf(
                                    separator,
                                    artPiece
                                )
                            )
                        ).collectAsLazyPagingItems()
                    )
                }
            }
        }

        composeTestRule.onNodeWithText("Rembrandt").assertIsDisplayed()
        composeTestRule.onNodeWithText("Landschap").performClick()
    }
}