package com.example.tinkoffmovies.domain.model

data class FilmModel(

    val id: Int,
    val title: String,
    val description: String,
    val poster: String,
    var isFavorite: Boolean = false
)