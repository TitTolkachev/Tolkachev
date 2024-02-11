package com.example.tolkachev.data.remote.repository

import com.example.tolkachev.data.remote.api.MovieApi
import com.example.tolkachev.data.remote.dto.MovieDetails
import com.example.tolkachev.data.remote.dto.PopularMoviesSheet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class MovieRepository(private val api: MovieApi) {

    suspend fun getPopularMovies(): Response<PopularMoviesSheet> = withContext(Dispatchers.IO) {
        return@withContext api.getPopularMovies()
    }

    suspend fun getMovieDetails(movieId: Long): Response<MovieDetails> =
        withContext(Dispatchers.IO) {
            return@withContext api.getMovieDetails(movieId)
        }
}