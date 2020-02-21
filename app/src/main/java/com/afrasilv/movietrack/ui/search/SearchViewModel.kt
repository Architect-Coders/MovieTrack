package com.afrasilv.movietrack.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import arrow.core.Either
import com.afrasilv.movietrack.ui.base.BaseViewModel
import com.afrasilv.movietrack.ui.base.convertToMovieInfo
import com.afrasilv.movietrack.ui.model.MovieInfo
import com.afrasilv.usecases.SearchMoviesByName
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchMoviesByName: SearchMoviesByName,
    uiDispatcher: CoroutineDispatcher
) : BaseViewModel(uiDispatcher) {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() = _model

    sealed class UiModel {
        data class Content(val movies: List<MovieInfo>) : UiModel()
        data class Navigation(val movie: MovieInfo) : UiModel()
    }

    fun searchMovies(name: String) {
        launch {
            when (val result = searchMoviesByName.invoke(name)) {
                is Either.Left -> {
                    //Error
                }
                is Either.Right -> {
                    _model.value = UiModel.Content(result.b.map { it.convertToMovieInfo() })
                }
            }
        }
    }

    fun movieClicked(movie: MovieInfo) {
        _model.value = UiModel.Navigation(movie)
    }
}