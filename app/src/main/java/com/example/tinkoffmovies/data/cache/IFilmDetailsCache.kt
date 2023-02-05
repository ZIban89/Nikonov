package com.example.tinkoffmovies.data.cache

import com.example.tinkoffmovies.data.dto.FilmDetailsDto

interface IFilmDetailsCache {

    fun saveFilmDetails(list: FilmDetailsDto)

    fun getFilmDetails(id: Int): FilmDetailsDto?

    fun clean()
}