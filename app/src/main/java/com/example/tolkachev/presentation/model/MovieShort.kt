package com.example.tolkachev.presentation.model

data class MovieShort(
    val id: Long,
    val name: String?,
    val description: String?,
    val isFavourite: Boolean,
    val posterUrl: String? = null,
)