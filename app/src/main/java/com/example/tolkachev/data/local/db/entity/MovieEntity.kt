package com.example.tolkachev.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey val id: Long,
    val name: String?,
    val description: String?,
    @ColumnInfo(name = "poster_url")
    val posterUrl: String? = null,
)
