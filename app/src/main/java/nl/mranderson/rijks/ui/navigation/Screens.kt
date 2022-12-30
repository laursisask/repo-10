package nl.mranderson.rijks.ui.navigation

sealed class Screens(val route: String, val arg: String = "") {
    object List : Screens("list_screen")
    object Detail : Screens("detail_screen", arg = "artId")
}