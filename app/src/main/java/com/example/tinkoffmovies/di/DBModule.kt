package com.example.tinkoffmovies.di

import android.content.Context
import androidx.room.Room
import com.example.tinkoffmovies.data.db.FilmsDao
import com.example.tinkoffmovies.data.db.FilmsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DBModule {

    @Provides
    @Singleton
    fun provideDB(context: Context): FilmsDao = Room.databaseBuilder(
        context,
        FilmsDatabase::class.java, "favorite_films"
    ).build().filmsDao()
}