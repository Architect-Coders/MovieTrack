package com.afrasilv.movietrack.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import arrow.core.Either
import com.afrasilv.movietrack.ui.base.BaseViewModel
import com.afrasilv.movietrack.ui.home.model.MovieInfo
import com.afrasilv.movietrack.ui.home.repository.MoviesRepository
import kotlinx.coroutines.launch

class DetailsViewModel(private val moviesRepository: MoviesRepository) : BaseViewModel() {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() = _model

    sealed class UiModel {
        object Loading : UiModel()
        class IsFav(val isFav: Boolean) : UiModel()
    }

    fun checkIsFav(idMovie: Int) {
        launch {
            if ((moviesRepository.checkIfMovieIsFav(idMovie) as Either.Right).b) {
                _model.value = UiModel.IsFav(true)
            } else {
                _model.value = UiModel.IsFav(false)
            }
        }
    }

    fun removeIfFav(movie: MovieInfo) {
        launch {
            moviesRepository.removeIfFav(movie)
            checkIsFav(movie.id)
        }
    }
}