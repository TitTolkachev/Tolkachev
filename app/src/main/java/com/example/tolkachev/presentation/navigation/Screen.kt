package com.example.tolkachev.presentation.navigation

sealed class Screen(val route: String, val title: String) {
    data object Popular : Screen(route = "popular", title = "Популярные")
    data object Favourite : Screen(route = "favourite", title = "Избранное")
}
