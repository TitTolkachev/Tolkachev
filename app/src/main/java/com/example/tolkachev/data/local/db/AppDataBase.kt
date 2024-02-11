package com.example.tolkachev.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tolkachev.data.local.db.dao.FavouriteMoviesDao
import com.example.tolkachev.data.local.db.entity.MovieEntity
import com.example.tolkachev.data.local.db.entity.MovieDetailsEntity

@Database(
    entities = [
        MovieEntity::class,
        MovieDetailsEntity::class
    ],
    version = 1,
    autoMigrations = []
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun movieDao(): FavouriteMoviesDao

    companion object {
        const val DB_NAME = "app_db"
    }
}