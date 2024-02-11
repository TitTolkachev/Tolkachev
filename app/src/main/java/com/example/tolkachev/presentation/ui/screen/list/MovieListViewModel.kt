package com.example.tolkachev.presentation.ui.screen.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tolkachev.data.remote.repository.MovieRepository
import com.example.tolkachev.presentation.model.MovieShort
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    private val _state: MutableStateFlow<MovieListState> = MutableStateFlow(MovieListState())
    val state: StateFlow<MovieListState> = _state.asStateFlow()

    init {
        loadPopularMovies()
    }

    private fun loadPopularMovies() {
        viewModelScope.launch {
            try {
                val movies = repository.getPopularMovies().body() ?: return@launch
                _state.update {
                    it.copy(movies = movies.films.map { f ->
                        MovieShort(f.filmId, f.nameRu, f.year.toString(), false, f.posterUrl)
                    })
                }
            } catch (_: Exception) {

            }
        }
    }
}

data class MovieListState(
    val movies: List<MovieShort>? = null,
)