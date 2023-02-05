package com.example.tinkoffmovies.data.cache

import com.example.tinkoffmovies.data.dto.FilmDto

interface IFilmsCache {

    fun getFilmsByPage(page: Int): List<FilmDto>?

    fun getAllFilms(): List<FilmDto>

    fun clean()

    fun addFilms(page: Int, films: List<FilmDto>)
}