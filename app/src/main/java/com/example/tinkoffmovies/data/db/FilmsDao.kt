package com.example.tinkoffmovies.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.tinkoffmovies.data.dto.FavoriteFilm

@Dao
interface FilmsDao {

    @Insert
    fun saveFavoriteId(film: FavoriteFilm)

    @Query("DELETE FROM favorite_films WHERE id = :id")
    fun deleteFavoriteId(id: Int)

    @Query("SELECT * FROM favorite_films")
    fun getAllFavorites(): List<FavoriteFilm>
}