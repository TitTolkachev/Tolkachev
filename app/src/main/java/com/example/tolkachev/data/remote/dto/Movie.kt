package com.example.tolkachev.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val filmId: Long,
    val nameRu: String?,
    val nameEn: String?,
    val year: String?,
    val filmLength: String?,
    val countries: List<Country>,
    val genres: List<Genre>,
    val rating: String?,
    val ratingVoteCount: Int?,
    val posterUrl: String?,
    val posterUrlPreview: String?,
    val ratingChange: String?,
    val isRatingUp: Boolean?,
    val isAfisha: Int?
)
