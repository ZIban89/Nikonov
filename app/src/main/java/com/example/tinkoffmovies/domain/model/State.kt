package com.example.tinkoffmovies.domain.model

sealed class State<T>(val data: T? = null, val error: String? = null) {

    class Error<Nothing>(error: String?): State<Nothing>(error = error)
    class Success<T>(data: T): State<T>(data = data)
    class Loading<Nothing>(): State<Nothing>()
}