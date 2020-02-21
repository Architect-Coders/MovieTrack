package com.afrasilv.movietrack.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import arrow.core.Either
import com.afrasilv.domain.Movie
import com.afrasilv.movietrack.ui.base.BaseViewModel
import com.afrasilv.movietrack.ui.base.convertToMovieInfo
import com.afrasilv.movietrack.ui.model.MovieInfo
import com.afrasilv.usecases.GetPopularMovies
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class HomeViewModel(private val getPopularMovies: GetPopularMovies, uiDispatcher: CoroutineDispatcher) : BaseViewModel(uiDispatcher) {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() = _model

    sealed class UiModel {
        object Loading : UiModel()
        data class Content(val movies: List<MovieInfo>) : UiModel()
        data class Navigation(val movie: MovieInfo) : UiModel()
    }

    fun discoverMovies() {
        launch {
            _model.value = UiModel.Loading
            when (val result = getPopularMovies.invoke()) {
                is Either.Left -> {
                    //Error
                }
                is Either.Right -> {
                    _model.value = UiModel.Content(result.b.map(Movie::convertToMovieInfo))
                }
            }
        }
    }

    fun movieClicked(movie: MovieInfo) {
        _model.value = UiModel.Navigation(movie)
    }
}