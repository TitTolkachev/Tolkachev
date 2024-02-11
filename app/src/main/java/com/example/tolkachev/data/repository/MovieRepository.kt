package com.example.tolkachev.data.repository

import com.example.tolkachev.data.local.db.dao.FavouriteMoviesDao
import com.example.tolkachev.data.local.db.entity.MovieDetailsEntity
import com.example.tolkachev.data.local.db.entity.MovieEntity
import com.example.tolkachev.data.remote.api.MovieApi
import com.example.tolkachev.data.remote.dto.PopularMoviesSheet
import com.example.tolkachev.presentation.model.MovieDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.Response

class MovieRepository(
    private val api: MovieApi,
    private val dao: FavouriteMoviesDao
) {

    suspend fun getPopularMovies(): Response<PopularMoviesSheet> = withContext(Dispatchers.IO) {
        return@withContext api.getPopularMovies()
    }

    fun getFavouriteMovies(): Flow<List<MovieEntity>> = dao.getAll()

    suspend fun addMovieToFavourite(
        movie: MovieEntity
    ) = withContext(Dispatchers.IO) {
        dao.insert(movie)
        val details = try {
            api.getMovieDetails(movie.id).body()
        } catch (_: Exception) {
            // TODO
            null
        }
        dao.insert(
            MovieDetailsEntity(
                id = movie.id,
                name = details?.nameRu,
                description = details?.description,
                genres = details?.genres?.joinToString(", ") { it.genre } ?: "",
                countries = details?.countries?.joinToString(", ") { it.country } ?: "",
                posterUrl = details?.posterUrl
            )
        )
    }

    suspend fun removeMovieFromFavourite(movieId: Long) = withContext(Dispatchers.IO) {
        dao.deleteById(movieId)
    }

    suspend fun getMovieDetails(movieId: Long): MovieDetails? =
        withContext(Dispatchers.IO) {
            var details = try {
                val response = api.getMovieDetails(movieId).body()
                if (response?.kinopoiskId != null) {
                    MovieDetails(
                        id = response.kinopoiskId,
                        name = response.nameRu,
                        description = response.description,
                        genres = response.genres.joinToString(", ") { it.genre },
                        countries = response.countries.joinToString(", ") { it.country },
                        posterUrl = response.posterUrl
                    )
                } else null
            } catch (_: Exception) {
                // TODO
                null
            }
            if (details == null) {
                val entity = dao.getDetailsById(movieId) ?: return@withContext null
                details = MovieDetails(
                    id = entity.id,
                    name = entity.name,
                    description = entity.description,
                    genres = entity.genres,
                    countries = entity.countries,
                    posterUrl = entity.posterUrl,
                )
            }

            return@withContext details
        }
}