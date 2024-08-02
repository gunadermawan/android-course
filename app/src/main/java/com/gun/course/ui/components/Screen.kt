package com.gun.course.ui.components


sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Data : Screen("data")
    object Profile : Screen("profile")
    object DetailScreen : Screen("detail/{id}") {
        fun createRoute(id: Int) = "detail/$id"
    }
}