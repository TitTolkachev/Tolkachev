package com.example.tolkachev.presentation.ui.screen.movie

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tolkachev.data.remote.repository.MovieRepository
import com.example.tolkachev.presentation.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: MovieRepository,
) : ViewModel() {
    private val movieId = savedStateHandle.get<Long>("movieId")

    private val _state: MutableStateFlow<MovieState> = MutableStateFlow(MovieState())
    val state: StateFlow<MovieState> = _state.asStateFlow()

    init {
        loadMovieDetails()
    }

    private fun loadMovieDetails() {
        viewModelScope.launch {
            try {
                if (movieId == null)
                    return@launch

                val details = repository.getMovieDetails(movieId).body() ?: return@launch

                if (details.kinopoiskId == null)
                    return@launch

                _state.update { s ->
                    s.copy(
                        movie = Movie(
                            id = details.kinopoiskId,
                            name = details.nameRu,
                            description = details.description,
                            genres = details.genres.joinToString(", ") { it.genre },
                            countries = details.countries.joinToString(", ") { it.country },
                            posterUrl = details.posterUrl
                        )
                    )
                }
            } catch (_: Exception) {

            }
        }
    }
}

data class MovieState(
    val movie: Movie? = null,
)