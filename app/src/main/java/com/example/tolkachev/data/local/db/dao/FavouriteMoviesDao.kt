package com.example.tolkachev.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.tolkachev.data.local.db.entity.MovieDetailsEntity
import com.example.tolkachev.data.local.db.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteMoviesDao {

    @Query("SELECT * FROM movie")
    fun getAll(): Flow<List<MovieEntity>>

    @Query("DELETE FROM movie WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Insert
    suspend fun insert(movie: MovieEntity): Long

    @Insert
    suspend fun insert(movie: MovieDetailsEntity)

    @Query("SELECT * FROM movie_details WHERE id = :id")
    suspend fun getDetailsById(id: Long): MovieDetailsEntity?
}