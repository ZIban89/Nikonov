package com.example.tinkoffmovies.data.dto

import com.squareup.moshi.Json

class FilmDetailsDto(

    val kinopoiskId: Int,
    val nameRu: String? = null,
    val nameEn: String? = null,
    val description: String? = null,
    val genres: List<GenreDto>,
    val countries: List<Country>,
    @field:Json(name = "posterUrl") val poster: String

)