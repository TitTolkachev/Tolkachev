package com.example.tolkachev.presentation.ui.screen.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tolkachev.data.local.db.entity.MovieEntity
import com.example.tolkachev.data.repository.MovieRepository
import com.example.tolkachev.presentation.model.Movie
import com.example.tolkachev.presentation.ui.screen.list.MovieListMode.FAVOURITE
import com.example.tolkachev.presentation.ui.screen.list.MovieListMode.POPULAR
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    private val popularMovies: MutableStateFlow<List<Movie>> = MutableStateFlow(listOf())
    private val favouriteMovies = repository.getFavouriteMovies().map { list ->
        list.map {
            Movie(
                id = it.id,
                name = it.name,
                description = it.description,
                isFavourite = true,
                posterUrl = it.posterUrl
            )
        }
    }

    private val _state: MutableStateFlow<MovieListState> =
        MutableStateFlow(MovieListState())
    val state = combine(
        _state,
        popularMovies,
        favouriteMovies
    ) { state, popular, favourite ->
        when (state.screenMode) {
            POPULAR -> state.copy(
                movies = popular.map {
                    it.copy(isFavourite = favourite.any { m -> m.id == it.id })
                }
            )

            FAVOURITE -> state.copy(movies = favourite)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), MovieListState())

    init {
        loadPopularMovies()
    }

    fun showPopular() {
        if (_state.value.screenMode != POPULAR) {
            _state.update { it.copy(screenMode = POPULAR) }
        }
    }

    fun showFavourite() {
        if (_state.value.screenMode != FAVOURITE) {
            _state.update { it.copy(screenMode = FAVOURITE) }
        }
    }

    fun onMovieCardClick(movieId: Long) {
        val movie = state.value.movies.find { it.id == movieId } ?: return

        viewModelScope.launch {
            if (movie.isFavourite) {
                repository.removeMovieFromFavourite(movieId)
            } else {
                repository.addMovieToFavourite(
                    MovieEntity(
                        movie.id,
                        movie.name,
                        movie.description,
                        movie.posterUrl
                    )
                )
            }
        }
    }

    fun loadPopularMovies() {
        viewModelScope.launch {
            _state.update { it.copy(popularMoviesLoading = true, requestError = false) }
            try {
                val movies = repository.getPopularMovies().body() ?: return@launch
                popularMovies.update {
                    movies.films.map {
                        Movie(
                            it.filmId,
                            it.nameRu,
                            it.year.toString(),
                            false,
                            it.posterUrl
                        )
                    }
                }
                _state.update { it.copy(popularMoviesLoading = false, requestError = false) }
            } catch (_: Exception) {
                // TODO
                _state.update { it.copy(popularMoviesLoading = false, requestError = true) }
            }
        }
    }
}

data class MovieListState(
    val movies: List<Movie> = listOf(),
    val searchQuery: String = "",
    val screenMode: MovieListMode = POPULAR,
    val popularMoviesLoading: Boolean = true,
    val requestError: Boolean = false,
)

enum class MovieListMode {
    POPULAR,
    FAVOURITE,
}