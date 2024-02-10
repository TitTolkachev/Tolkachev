package com.example.tolkachev.presentation.navigation

sealed class Screen(val route: String) {
    data object MovieList : Screen(route = "list")
    data object Movie : Screen(route = "movie")
}