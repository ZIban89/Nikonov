package com.example.tinkoffmovies.data.mapper

import android.content.Context
import com.example.tinkoffmovies.R
import com.example.tinkoffmovies.data.dto.FilmDetailsDto
import com.example.tinkoffmovies.data.dto.FilmDto
import com.example.tinkoffmovies.domain.model.FilmDetailsModel
import com.example.tinkoffmovies.domain.model.FilmModel

private const val SEPARATOR = ", "

fun FilmDto.map() =
    FilmModel(
        id = filmId,
        title = nameRu ?: nameEn ?: "",
        description = "${genres.firstOrNull()?.genre?.replaceFirstChar { it.uppercase() } ?: ""}${year?.let { " ($it)" } ?: ""}",
        poster = poster
    )

fun FilmDetailsDto.map(context: Context): FilmDetailsModel {
    val genresText = genres.takeIf { it.isNotEmpty() }?.let { list ->
        context.getString(
            R.string.genres,
            list.map { it.genre }.joinToString(SEPARATOR)
        )
    } ?: ""
    val countriesText = countries.takeIf { it.isNotEmpty() }?.let { list ->
        context.getString(
            R.string.countries,
            list.map { it.country }.joinToString(SEPARATOR)
        )
    } ?: ""
    return FilmDetailsModel(
        id = kinopoiskId,
        title = nameRu ?: nameEn ?: "",
        description = description ?: "",
        genres = genresText,
        countries = countriesText,
        poster = poster
    )
}


