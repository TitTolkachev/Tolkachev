package com.example.tolkachev.data.remote.api

import com.example.tolkachev.data.remote.dto.MovieDetails
import com.example.tolkachev.data.remote.dto.PopularMoviesSheet
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {

    @GET("films/top?type=TOP_100_POPULAR_FILMS")
    suspend fun getPopularMovies(): Response<PopularMoviesSheet>

    @GET("films/{movieId}")
    suspend fun getMovieDetails(@Path("movieId") movieId: Long): Response<MovieDetails>
}