package com.example.tolkachev.presentation.model

data class MovieDetails(
    val id: Long,
    val name: String?,
    val description: String?,
    val genres: String,
    val countries: String,
    val posterUrl: String? = null,
)
