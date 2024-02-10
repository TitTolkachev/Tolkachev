package com.example.tolkachev.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tolkachev.presentation.ui.screen.favourite.FavouriteMoviesScreen
import com.example.tolkachev.presentation.ui.screen.popular.PopularMoviesScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Popular.route,
        modifier = modifier
    ) {
        composable(
            route = Screen.Popular.route
        ) {
            PopularMoviesScreen()
        }
        composable(
            route = Screen.Favourite.route
        ) {
            FavouriteMoviesScreen()
        }
    }
}