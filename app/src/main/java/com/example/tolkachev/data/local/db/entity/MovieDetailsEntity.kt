package com.example.tolkachev.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "movie_details",
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MovieDetailsEntity(
    @PrimaryKey
    @ColumnInfo(index = true)
    val id: Long = 0,
    val name: String?,
    val description: String?,
    val genres: String,
    val countries: String,
    @ColumnInfo(name = "poster_url")
    val posterUrl: String? = null,
)
