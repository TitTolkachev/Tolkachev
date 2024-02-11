package com.example.tolkachev.presentation.ui.screen.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tolkachev.data.local.db.entity.MovieEntity
import com.example.tolkachev.data.repository.MovieRepository
import com.example.tolkachev.presentation.model.Movie
import com.example.tolkachev.presentation.ui.screen.list.MovieListMode.FAVOURITE
import com.example.tolkachev.presentation.ui.screen.list.MovieListMode.POPULAR
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale

class MovieListViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    private var updateSearchQueryJob: Job? = null

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

    private val _searchState = MutableStateFlow(SearchState())
    val searchState = _searchState.asStateFlow()

    private val _state: MutableStateFlow<MovieListState> =
        MutableStateFlow(MovieListState())
    val state = combine(
        _state,
        popularMovies,
        favouriteMovies
    ) { state, popular, favourite ->
        if (state.isSearching)
            return@combine state

        if (_searchState.value.enabled) {
            when (state.screenMode) {
                POPULAR -> state.copy(
                    movies = popular.map {
                        it.copy(isFavourite = favourite.any { m -> m.id == it.id })
                    }.filter {
                        it.name?.uppercase()?.contains(state.searchQuery.uppercase()) == true
                    }
                )

                FAVOURITE -> state.copy(movies = favourite.filter {
                    it.name?.uppercase()?.contains(state.searchQuery.uppercase()) == true
                })
            }
        } else {
            when (state.screenMode) {
                POPULAR -> state.copy(
                    movies = popular.map {
                        it.copy(isFavourite = favourite.any { m -> m.id == it.id })
                    }
                )

                FAVOURITE -> state.copy(movies = favourite)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), MovieListState())

    init {
        loadPopularMovies()
    }

    fun updateSearchText(newValue: String) {
        _searchState.update { it.copy(text = newValue) }
        updateSearchQueryJob?.cancel()
        updateSearchQueryJob = viewModelScope.launch {
            delay(300L)
            _state.update { it.copy(isSearching = true) }
            delay(1000L)
            _state.update { it.copy(searchQuery = newValue, isSearching = false) }
        }
    }

    fun enableSearch() {
        _searchState.update { it.copy(enabled = true) }
        _state.update { it.copy(searchQuery = "", isSearching = false) }
    }

    fun disableSearch() {
        _searchState.update { it.copy(enabled = false, text = "") }
        _state.update { it.copy(searchQuery = "", isSearching = false) }
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
                    movies.films.map { movie ->
                        Movie(
                            movie.filmId,
                            movie.nameRu,
                            "${
                                (movie.genres.getOrNull(0)?.genre ?: "").replaceFirstChar {
                                    if (it.isLowerCase()) it.titlecase(
                                        Locale.ROOT
                                    ) else it.toString()
                                }
                            } (${movie.year})",
                            false,
                            movie.posterUrl
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
    val isSearching: Boolean = false,
)

data class SearchState(
    val enabled: Boolean = false,
    val text: String = "",
)

enum class MovieListMode {
    POPULAR,
    FAVOURITE,
}