package nl.mranderson.rijks.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import nl.mranderson.rijks.ui.detail.DetailScreen
import nl.mranderson.rijks.ui.list.ListScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.List.route
    )
    {
        composable(route = Screens.List.route) {
            ListScreen(onArtClicked = { id ->
                navController.navigate(Screens.Detail.route.plus("/$id"))
            })
        }
        composable(route = "${Screens.Detail.route}/{${Screens.Detail.argArtId}}") { backStackEntry ->
            backStackEntry.arguments?.getString(Screens.Detail.argArtId)?.let {
                DetailScreen(
                    onBackClicked = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}