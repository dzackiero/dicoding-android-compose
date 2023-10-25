package com.pnj.jetbraintoolbox.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Profile : Screen("profile")
    object Detail : Screen("home/{name}") {
        fun createRoute(name: String) = "home/$name"
    }
}