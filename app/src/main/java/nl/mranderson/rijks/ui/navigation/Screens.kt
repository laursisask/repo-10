package nl.mranderson.rijks.ui.navigation

sealed class Screens(val route: String) {
    object List : Screens("list_screen")
    object Detail : Screens("detail_screen") {
        const val argArtId = "artId"
    }
}