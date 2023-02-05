package com.example.tinkoffmovies.domain.repo

import com.example.tinkoffmovies.domain.model.FilmDetailsModel
import com.example.tinkoffmovies.domain.model.FilmModel

interface IFilmsRepo {

    suspend fun getTopFilms(page: Int, isFavorites: Boolean): List<FilmModel>

    suspend fun getFilmDetails(id: Int): FilmDetailsModel

    fun updateFavorite(id: Int)

    fun cleanDetailsCache()

    fun cleanFilmsCache()
}