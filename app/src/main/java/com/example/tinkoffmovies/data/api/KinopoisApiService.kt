package com.example.tinkoffmovies.data.api

import com.example.tinkoffmovies.common.*
import com.example.tinkoffmovies.data.dto.FilmDetailsDto
import com.example.tinkoffmovies.data.dto.FilmsTopDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query



interface KinopoisApiService {

    @GET("films/top")
    suspend fun getFilmsTopList(
        @Query(PAGE_KEY) page: Int,
        @Query(TYPE_KEY) type: String = TYPE,
        @Header(API_KEY) key: String = KEY
    ): FilmsTopDto

    @GET("films/{id}")
    suspend fun getFilmDetails(
        @Path("id") id: Int,
        @Header(API_KEY) key: String = KEY
    ): FilmDetailsDto
}