package com.example.tolkachev.presentation.ui.screen.movie

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.example.tolkachev.presentation.model.MovieDetails
import com.example.tolkachev.presentation.theme.AppTheme
import com.example.tolkachev.presentation.ui.screen.list.components.RequestError
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieScreen(onBackClick: () -> Unit) {
    val viewModel: MovieViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    MovieContent(
        movie = state.movie,
        isLoading = state.isLoading,
        isError = state.isError,
        onRefresh = remember { { viewModel.loadMovieDetails() } },
        onBackClick = onBackClick
    )
}

@Composable
private fun MovieContent(
    movie: MovieDetails?,
    isLoading: Boolean = false,
    isError: Boolean = false,
    onRefresh: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    BackButton(onClick = onBackClick)
    if (isError) {
        RequestError(onClick = onRefresh)
    } else if (isLoading || movie == null) {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    } else {
        Column(
            Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(),
                model = movie.posterUrl,
                contentScale = ContentScale.FillWidth,
                contentDescription = null
            )
            Column(modifier = Modifier.padding(horizontal = 32.dp)) {
                Spacer(Modifier.height(20.dp))
                Text(
                    text = movie.name ?: "Unknown",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(Modifier.height(16.dp))
                if (movie.description?.isNotBlank() == true) {
                    Text(
                        text = AnnotatedString(movie.description),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
                if (movie.genres.isNotBlank()) {
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = buildAnnotatedString {
                            withStyle(MaterialTheme.typography.labelMedium.toSpanStyle()) {
                                append("Жанры: ")
                            }
                            withStyle(MaterialTheme.typography.labelSmall.toSpanStyle()) {
                                append(movie.genres)
                            }
                        }
                    )
                }
                if (movie.countries.isNotBlank()) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = buildAnnotatedString {
                            withStyle(MaterialTheme.typography.labelMedium.toSpanStyle()) {
                                append("Страны: ")
                            }
                            withStyle(MaterialTheme.typography.labelSmall.toSpanStyle()) {
                                append(movie.countries)
                            }
                        }
                    )
                }
                Spacer(Modifier.height(32.dp))
            }
            Spacer(Modifier.weight(1f))
        }
    }
}

@Composable
private fun BackButton(onClick: () -> Unit) {
    IconButton(
        modifier = Modifier
            .zIndex(1f)
            .padding(vertical = 21.dp, horizontal = 16.dp),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)
        ),
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        MovieContent(
            MovieDetails(
                id = 0,
                name = "Изгой-один: Звёздные войны",
                description = "Сопротивление собирает отряд для выполнения особой миссии - надо выкрасть чертежи самого совершенного и мертоносного оружия Империи. Не всем суждено вернуться домой, но герои готовы к этому, ведь на кону судьба Галактики",
                genres = "фантастика, приключения",
                countries = "США"
            )
        )
    }
}
