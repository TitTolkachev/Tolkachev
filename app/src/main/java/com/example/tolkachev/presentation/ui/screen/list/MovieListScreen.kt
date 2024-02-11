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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tolkachev.presentation.model.MovieShort
import com.example.tolkachev.presentation.theme.AppTheme
import com.example.tolkachev.presentation.ui.screen.list.components.BottomNavBar
import com.example.tolkachev.presentation.ui.screen.list.components.MovieCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieListScreen(onMovieClick: (Long) -> Unit) {
    val viewModel: MovieListViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    MovieListContent(state.movies, onMovieClick)
}

@Composable
private fun MovieListContent(
    movies: List<MovieShort>?,
    onMovieClick: (Long) -> Unit = {}
) {
    Scaffold(
        topBar = {},
        bottomBar = { BottomNavBar() }
    ) { paddingValues ->
        if (movies == null) {
            Box(Modifier.fillMaxSize()) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
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
                        onClick = { onMovieClick(it.id) }
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
                MovieShort(0, "Movie 1", "Description 1", true),
                MovieShort(1, "Movie 2", "Description 2", false),
                MovieShort(2, "Movie 3", "Description 3", true),
                MovieShort(3, "Movie 4", "Description 4", true),
                MovieShort(4, "Movie 5", "Description 5", false),
                MovieShort(5, "Movie 6", "Description 6", false),
            )
        )
    }
}