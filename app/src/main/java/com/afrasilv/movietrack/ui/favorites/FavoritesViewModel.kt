package com.afrasilv.movietrack.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import arrow.core.Either
import com.afrasilv.movietrack.ui.base.BaseViewModel
import com.afrasilv.movietrack.ui.base.convertToMovieInfo
import com.afrasilv.movietrack.ui.model.MovieInfo
import com.afrasilv.usecases.GetFavoriteMovies
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val getFavoriteMovies: GetFavoriteMovies,
    uiDispatcher: CoroutineDispatcher
) : BaseViewModel(uiDispatcher) {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() = _model

    sealed class UiModel {
        data class Content(val movies: List<MovieInfo>) : UiModel()
        data class Navigation(val movie: MovieInfo) : UiModel()
    }

    fun getFavoriteMovies() {
        coroutineContext.plus(launch(Dispatchers.IO) {
            when (val result = getFavoriteMovies.invoke()) {
                is Either.Left -> {
                    //Error
                }
                is Either.Right -> {
                    _model.postValue(UiModel.Content(result.b.map { it.convertToMovieInfo() }))
                }
            }
        })
    }

    fun movieClicked(movie: MovieInfo) {
        _model.value = UiModel.Navigation(movie)
    }
}