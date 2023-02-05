package com.example.tinkoffmovies.data.dto

import com.squareup.moshi.Json

data class FilmDto(

    val filmId: Int,
    val nameRu: String? = null,
    val nameEn: String? = null,
    val year: Int? = null,
    val genres: List<GenreDto>,
    @field:Json(name = "posterUrlPreview") val poster: String
)