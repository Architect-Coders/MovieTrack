package com.afrasilv.movietrack.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import arrow.core.Either
import com.afrasilv.movietrack.ui.base.BaseViewModel
import com.afrasilv.movietrack.ui.home.model.MovieInfo
import com.afrasilv.movietrack.ui.home.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesViewModel(private val moviesRepository: MoviesRepository) : BaseViewModel() {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() = _model

    sealed class UiModel {
        object Loading : UiModel()
        data class Content(val movies: List<MovieInfo>) : UiModel()
        data class Navigation(val movie: MovieInfo) : UiModel()
    }

    fun getFavoriteMovies() {
        coroutineContext.plus(launch(Dispatchers.IO) {
            when (val result = moviesRepository.getFavoriteMovies()) {
                is Either.Left -> {
                    //Error
                }
                is Either.Right -> {
                    _model.postValue(UiModel.Content(result.b))
                }
            }
        })
    }

    fun movieClicked(movie: MovieInfo) {
        _model.value = UiModel.Navigation(movie)
    }
}