package com.example.tinkoffmovies.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_films")
class FavoriteFilm (@PrimaryKey(autoGenerate = false) val id: Int)