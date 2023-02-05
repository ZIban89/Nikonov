package com.example.tinkoffmovies.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tinkoffmovies.common.LAST_PAGE
import com.example.tinkoffmovies.domain.model.FilmDetailsModel
import com.example.tinkoffmovies.domain.model.FilmModel
import com.example.tinkoffmovies.domain.model.State
import com.example.tinkoffmovies.domain.repo.IFilmsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

// Костыль. Вообще нужно было делать interactor и такое делать в нём.
// Но сразу в нём пользы не увидел (кроме как тестами обмазать), а теперь лень.
private const val INTERNET_EXCEPTION_TEXT =
    "Интернет накрылся. Проверьте настройки или смените провайдера и попробйте вновь."

@HiltViewModel
class FilmsViewModel @Inject constructor(

    private val repo: IFilmsRepo
) : ViewModel() {

    private var nextPage = 1
    var isFavoriteScreen: Boolean = false

    private val _filmsFlow: MutableStateFlow<State<List<FilmModel>>> =
        MutableStateFlow(State.Loading())
    val filmsFlow = _filmsFlow.asStateFlow()

    private val _filmDetailsFlow: MutableStateFlow<State<FilmDetailsModel>> =
        MutableStateFlow(State.Loading())
    val filmDetailsFlow = _filmDetailsFlow.asStateFlow()

    init {
        getFilms()
    }

    fun getFilms() {
        if (nextPage > LAST_PAGE) return
        _filmsFlow.value = State.Loading()
        viewModelScope.launch {
            _filmsFlow.value = try {
                State.Success(data = repo.getTopFilms(nextPage, isFavoriteScreen))
            } catch (e: UnknownHostException) {
                State.Error(INTERNET_EXCEPTION_TEXT)
            } catch (t: Throwable) {
                State.Error(t.message)
            }
        }
    }

    fun loadNextFilms() {
        getFilms()
        nextPage++
    }

    fun updateFilms() {
        nextPage = 1
        repo.cleanFilmsCache()
        getFilms()
    }

    fun getFilmDetails(id: Int) {
        _filmDetailsFlow.value = State.Loading()
        viewModelScope.launch {
            _filmDetailsFlow.value = try {
                State.Success(data = repo.getFilmDetails(id))
            } catch (t: Throwable) {
                State.Error(t.message)
            }
        }
    }

    fun addToFavorite(id: Int) {
        viewModelScope.launch {
            repo.updateFavorite(id)
            getFilms()
        }
    }

    fun switchFavoriteScreen(isFavorite: Boolean) {
        isFavoriteScreen = isFavorite
        getFilms()
    }
}