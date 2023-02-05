package com.example.tinkoffmovies.data.repo

import android.content.Context
import com.example.tinkoffmovies.common.PAGE_SIZE
import com.example.tinkoffmovies.data.api.KinopoisApiService
import com.example.tinkoffmovies.data.cache.IFilmDetailsCache
import com.example.tinkoffmovies.data.cache.IFilmsCache
import com.example.tinkoffmovies.data.mapper.map
import com.example.tinkoffmovies.domain.model.FilmDetailsModel
import com.example.tinkoffmovies.domain.model.FilmModel
import com.example.tinkoffmovies.domain.repo.IFilmsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FilmsRepo @Inject constructor(

    private val apiService: KinopoisApiService,
    private val filmDetailsCache: IFilmDetailsCache,
    private val filmsCache: IFilmsCache,
    private val context: Context
) : IFilmsRepo {


    private val favorites = mutableListOf<Int>()

    override suspend fun getTopFilms(page: Int, isFavorites: Boolean): List<FilmModel> {
        val films = filmsCache.getFilmsByPage(page)
        if (films == null || films.size < PAGE_SIZE) {
            withContext(Dispatchers.IO) {
                apiService.getFilmsTopList(page).films
                    .also { filmsCache.addFilms(page, it) }
            }
        }
        val result = filmsCache.getAllFilms()
            .map { it.map() }
            .onEach {
                if (favorites.contains(it.id)) it.isFavorite = true
            }
        return if (isFavorites) result.filter { it.isFavorite } else result
    }

    override suspend fun getFilmDetails(id: Int): FilmDetailsModel {
        return withContext(Dispatchers.IO) {
            (filmDetailsCache.getFilmDetails(id) ?: apiService.getFilmDetails(id).also {
                filmDetailsCache.saveFilmDetails(it)
            }).map(context)
        }
    }

    override fun updateFavorite(id: Int) {
        if (favorites.contains(id)) favorites.remove(id) else favorites.add(id)
    }

    override fun cleanDetailsCache() {
        filmDetailsCache.clean()
    }

    override fun cleanFilmsCache() {
        filmsCache.clean()
    }
}