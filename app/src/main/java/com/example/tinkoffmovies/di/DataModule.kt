package com.example.tinkoffmovies.di

import com.example.tinkoffmovies.data.cache.FilmDetailsCache
import com.example.tinkoffmovies.data.cache.FilmsCache
import com.example.tinkoffmovies.data.cache.IFilmDetailsCache
import com.example.tinkoffmovies.data.cache.IFilmsCache
import com.example.tinkoffmovies.data.repo.FilmsRepo
import com.example.tinkoffmovies.domain.repo.IFilmsRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindFilmsRepo(repo: FilmsRepo): IFilmsRepo

    @Binds
    fun bindFilmDetailsCache(cache: FilmDetailsCache): IFilmDetailsCache

    @Binds
    fun bindFilmsCache(cache: FilmsCache): IFilmsCache
}