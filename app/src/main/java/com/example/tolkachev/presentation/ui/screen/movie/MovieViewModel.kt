package com.example.tolkachev.presentation.ui.screen.movie

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tolkachev.data.repository.MovieRepository
import com.example.tolkachev.presentation.model.MovieDetails
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

    fun loadMovieDetails() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, isError = false) }
            try {
                if (movieId == null) {
                    _state.update { it.copy(isLoading = false, isError = true) }
                    return@launch
                }
                val details = repository.getMovieDetails(movieId)
                if (details == null) {
                    _state.update { it.copy(isLoading = false, isError = true) }
                    return@launch
                }
                _state.update { it.copy(movie = details, isLoading = false, isError = false) }
            } catch (_: Exception) {
                // TODO
                _state.update { it.copy(isLoading = false, isError = true) }
            }
        }
    }
}

data class MovieState(
    val movie: MovieDetails? = null,
    val isLoading: Boolean = true,
    val isError: Boolean = false,
)