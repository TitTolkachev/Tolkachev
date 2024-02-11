package com.example.tolkachev.presentation.ui.screen.list

import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tolkachev.presentation.model.Movie
import com.example.tolkachev.presentation.theme.AppTheme
import com.example.tolkachev.presentation.ui.screen.list.MovieListMode.POPULAR
import com.example.tolkachev.presentation.ui.screen.list.components.BottomNavBar
import com.example.tolkachev.presentation.ui.screen.list.components.MovieCard
import com.example.tolkachev.presentation.ui.screen.list.components.RequestError
import com.example.tolkachev.presentation.ui.screen.list.components.TopBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieListScreen(onMovieClick: (Long) -> Unit) {
    val viewModel: MovieListViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    MovieListContent(
        movies = state.movies,
        dataLoading = state.popularMoviesLoading && state.screenMode == POPULAR,
        showRequestError = state.requestError && state.screenMode == POPULAR,
        screenMode = state.screenMode,
        onMovieClick = onMovieClick,
        onMovieLongClick = remember { { viewModel.onMovieCardClick(it) } },
        onShowPopular = remember { { viewModel.showPopular() } },
        onShowFavourite = remember { { viewModel.showFavourite() } },
        onRefresh = remember { { viewModel.loadPopularMovies() } },
    )
}

@Composable
private fun MovieListContent(
    movies: List<Movie>,
    dataLoading: Boolean = false,
    showRequestError: Boolean = false,
    screenMode: MovieListMode = POPULAR,
    searchQuery: String = "",
    onMovieClick: (Long) -> Unit = {},
    onMovieLongClick: (Long) -> Unit = {},
    onShowPopular: () -> Unit = {},
    onShowFavourite: () -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopBar(
                title = if (screenMode == POPULAR) "Популярные" else "Избранное",
                query = searchQuery
            )
        },
        bottomBar = {
            BottomNavBar(
                active = if (screenMode == POPULAR) 1 else 2,
                onFirstClick = onShowPopular,
                onSecondClick = onShowFavourite
            )
        }
    ) { paddingValues ->
        if (dataLoading) {
            Box(Modifier.fillMaxSize()) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        } else if (showRequestError) {
            RequestError(onRefresh)
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(vertical = 32.dp),
                verticalArrangement = spacedBy(12.dp),
            ) {
                items(items = movies, key = { it.id }) {
                    MovieCard(
                        movie = it,
                        onClick = { onMovieClick(it.id) },
                        onLongClick = { onMovieLongClick(it.id) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        MovieListContent(
            listOf(
                Movie(0, "Movie 1", "Description 1", true),
                Movie(1, "Movie 2", "Description 2", false),
                Movie(2, "Movie 3", "Description 3", true),
                Movie(3, "Movie 4", "Description 4", true),
                Movie(4, "Movie 5", "Description 5", false),
                Movie(5, "Movie 6", "Description 6", false),
            )
        )
    }
}