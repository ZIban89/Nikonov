package com.example.tinkoffmovies.data.cache

import com.example.tinkoffmovies.common.PAGE_SIZE
import com.example.tinkoffmovies.data.dto.FilmDto
import javax.inject.Inject

class FilmsCache @Inject constructor() : IFilmsCache {

    private val list = mutableListOf<FilmDto>()

    override fun getFilmsByPage(page: Int): List<FilmDto>? {
        val startIndex = (page - 1) * PAGE_SIZE
        if (startIndex >= list.size) return null
        val lastIndex = list.size.coerceAtMost(startIndex + PAGE_SIZE)
        return list.subList(startIndex, lastIndex)
    }

    override fun getAllFilms(): List<FilmDto> {
        return list.toList()
    }

    override fun clean() {
        list.clear()
    }

    override fun addFilms(page: Int, films: List<FilmDto>) {
        getFilmsByPage(page)?.let { list.removeAll(it) }
        list.addAll(films)
    }
}