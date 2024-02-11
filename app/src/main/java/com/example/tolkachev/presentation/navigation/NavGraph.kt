package com.example.tolkachev.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tolkachev.presentation.ui.screen.list.MovieListScreen
import com.example.tolkachev.presentation.ui.screen.movie.MovieScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.MovieList.route,
        modifier = modifier
    ) {
        composable(
            route = Screen.MovieList.route,
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
        ) {
            MovieListScreen(
                onMovieClick = { movieId ->
                    navController.navigate(Screen.Movie.route + "/$movieId")
                }
            )
        }
        composable(
            route = Screen.Movie.route + "/{movieId}",
            enterTransition = { enterTransition() },
            popExitTransition = { exitTransition() },
            arguments = listOf(navArgument("movieId") {
                type = NavType.LongType
            })
        ) {
            MovieScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

/**
 * Shows transition animation after navigating to screen.
 */
private fun AnimatedContentTransitionScope<NavBackStackEntry>.enterTransition(): EnterTransition {
    return slideIntoContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Up,
        animationSpec = tween(400, easing = FastOutSlowInEasing)
    )
}

/**
 * Shows transition animation after navigating from screen.
 */
private fun AnimatedContentTransitionScope<NavBackStackEntry>.exitTransition(): ExitTransition {
    return slideOutOfContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Down,
        animationSpec = tween(300, easing = FastOutSlowInEasing)
    )
}