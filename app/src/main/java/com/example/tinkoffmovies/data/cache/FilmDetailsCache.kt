package com.example.tinkoffmovies.data.cache

import com.example.tinkoffmovies.data.dto.FilmDetailsDto
import javax.inject.Inject

class FilmDetailsCache @Inject constructor() : IFilmDetailsCache {

    private val map = mutableMapOf<Int, FilmDetailsDto>()

    override fun saveFilmDetails(filmDetails: FilmDetailsDto) {
        map[filmDetails.kinopoiskId] = filmDetails
    }

    override fun getFilmDetails(id: Int): FilmDetailsDto? = map[id]

    override fun clean() {
        map.clear()
    }
}