package nl.mranderson.rijks.ui.navigation

sealed class Screens(val route: String) {
    data object List : Screens("list_screen")
    data object Detail : Screens("detail_screen") {
        const val argArtId = "artId"
    }
    data object Image : Screens("image_screen") {
        const val argArtImageUrl = "artImageUrl"
    }
}
