package nl.mranderson.rijks.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import nl.mranderson.rijks.ui.detail.DetailScreen
import nl.mranderson.rijks.ui.detail.DetailViewModel
import nl.mranderson.rijks.ui.list.ListScreen
import nl.mranderson.rijks.ui.list.ListViewModel

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.List.route
    )
    {
        composable(route = Screens.List.route) {

            val listViewModel: ListViewModel = hiltViewModel()
            val lazyArtCollection = listViewModel.artCollectionFlow.collectAsLazyPagingItems()

            ListScreen(
                artCollection = lazyArtCollection,
                onArtClicked = { id ->
                    navController.navigate(Screens.Detail.route.plus("/$id"))
                }
            )
        }

        composable(route = "${Screens.Detail.route}/{${Screens.Detail.argArtId}}") { backStackEntry ->
            backStackEntry.arguments?.getString(Screens.Detail.argArtId)?.let {

                val viewModel: DetailViewModel = hiltViewModel()
                val detailState by viewModel.state.collectAsStateWithLifecycle()

                DetailScreen(
                    viewData = detailState,
                    onRetryClicked = {
                        viewModel.onRetryClicked()
                    },
                    onBackClicked = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}