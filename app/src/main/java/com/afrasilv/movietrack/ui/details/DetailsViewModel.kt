package com.afrasilv.movietrack.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import arrow.core.Either
import com.afrasilv.movietrack.Event
import com.afrasilv.movietrack.ui.base.BaseViewModel
import com.afrasilv.movietrack.ui.details.model.Cast
import com.afrasilv.movietrack.ui.details.repository.CreditsRepository
import com.afrasilv.movietrack.ui.home.HomeViewModel
import com.afrasilv.movietrack.ui.home.model.MovieInfo
import com.afrasilv.movietrack.ui.home.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel(private val moviesRepository: MoviesRepository) : BaseViewModel() {

    private val _model = MutableLiveData<Event<UiModel>>()
    val model: LiveData<Event<UiModel>>
        get() = _model

    sealed class UiModel {
        object Loading : UiModel()
        class IsFav(val isFav: Boolean) : UiModel()
        class ShowCast(val castList: List<Cast>) : UiModel()
        class Navigation(val cast: Cast): UiModel()
    }

    fun checkIsFav(idMovie: Int) {
        launch(Dispatchers.IO) {
            if ((moviesRepository.checkIfMovieIsFav(idMovie) as Either.Right).b) {
                _model.postValue(Event(UiModel.IsFav(true)))
            } else {
                _model.postValue(Event(UiModel.IsFav(false)))
            }
        }
    }

    fun removeIfFav(movie: MovieInfo) {
        launch {
            moviesRepository.removeIfFav(movie)
            checkIsFav(movie.id)
        }
    }

    fun getCredits(movieId: Int) {
        launch(Dispatchers.IO) {
            when (val response = CreditsRepository.discoverMoviesByPopularity(movieId)) {
                is Either.Right -> _model.postValue(Event(UiModel.ShowCast(response.b)))
            }
        }
    }

    fun movieClicked(cast: Cast) {
        _model.value = Event(UiModel.Navigation(cast))
    }
}