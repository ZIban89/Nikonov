package com.example.tinkoffmovies.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tinkoffmovies.data.dto.FavoriteFilm

@Database(entities = [FavoriteFilm::class], version = 1)
abstract class FilmsDatabase : RoomDatabase() {

    abstract fun filmsDao(): FilmsDao
}