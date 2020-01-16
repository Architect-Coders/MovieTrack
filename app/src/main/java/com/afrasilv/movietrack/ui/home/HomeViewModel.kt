package com.afrasilv.movietrack.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import arrow.core.Either
import com.afrasilv.movietrack.ui.base.BaseViewModel
import com.afrasilv.movietrack.ui.home.model.MovieInfo
import com.afrasilv.movietrack.ui.home.repository.MoviesRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val moviesRepository: MoviesRepository) : BaseViewModel() {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() = _model

    sealed class UiModel {
        object Loading : UiModel()
        data class Content(val movies: List<MovieInfo>) : UiModel()
        data class Navigation(val movie: MovieInfo) : UiModel()
        object RequestLocationPermission : UiModel()
    }

    fun discoverMovies() {
        coroutineContext.plus(launch {
            when (val result = moviesRepository.discoverMoviesByPopularity()) {
                is Either.Left -> {
                    //Error
                }
                is Either.Right -> {
                    _model.value = UiModel.Content(result.b)
                }
            }
        })
    }

    fun movieClicked(movie: MovieInfo) {
        _model.value = UiModel.Navigation(movie)
    }
}