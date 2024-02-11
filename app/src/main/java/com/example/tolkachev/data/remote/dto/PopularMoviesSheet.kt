package com.example.tolkachev.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PopularMoviesSheet(
    val pagesCount: Int,
    val films: List<Movie>
)
