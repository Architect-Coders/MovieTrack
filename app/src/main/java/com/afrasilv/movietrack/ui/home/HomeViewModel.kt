package com.afrasilv.movietrack.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import arrow.core.Either
import com.afrasilv.movietrack.ui.base.BaseViewModel
import com.afrasilv.movietrack.ui.home.model.MovieInfo
import com.afrasilv.movietrack.ui.home.repository.MoviesRepository
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel() {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() = _model

    sealed class UiModel {
        object Loading : UiModel()
        data class Content(val movies: List<MovieInfo>) : UiModel()
        data class Navigation(val movie: MovieInfo) : UiModel()
        object RequestLocationPermission : UiModel()
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun discoverMovies() {
        coroutineContext.plus(launch {
            when (val result = MoviesRepository.discoverMoviesByPopularity()) {
                is Either.Left -> {
                    //Error
                }
                is Either.Right -> {
                    _model.value = UiModel.Content(result.b.results)
                }
            }
        })
    }
}