package com.afrasilv.movietrack.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import arrow.core.Either
import com.afrasilv.movietrack.Event
import com.afrasilv.movietrack.data.convertToMovie
import com.afrasilv.movietrack.ui.base.BaseViewModel
import com.afrasilv.movietrack.ui.base.convertToCast
import com.afrasilv.movietrack.ui.details.model.Cast
import com.afrasilv.movietrack.ui.model.MovieInfo
import com.afrasilv.usecases.CheckIfMovieIsFav
import com.afrasilv.usecases.GetMovieCredits
import com.afrasilv.usecases.RemoveIfFav
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val checkIfMovieIsFav: CheckIfMovieIsFav,
    private val removeIfFav: RemoveIfFav,
    private val getMovieCredits: GetMovieCredits,
    uiDispatcher: CoroutineDispatcher
) : BaseViewModel(uiDispatcher) {

    private val _model = MutableLiveData<Event<UiModel>>()
    val model: LiveData<Event<UiModel>>
        get() = _model

    sealed class UiModel {
        class IsFav(val isFav: Boolean) : UiModel()
        class ShowCast(val castList: List<Cast>) : UiModel()
        class Navigation(val cast: Cast) : UiModel()
    }

    fun checkIsFav(idMovie: Int) {
        launch {
            if (checkIfMovieIsFav.invoke(idMovie)) {
                _model.value = Event(UiModel.IsFav(true))
            } else {
                _model.value = Event(UiModel.IsFav(false))
            }
        }
    }

    fun removeIfFav(movie: MovieInfo) {
        launch {
            removeIfFav.invoke(movie.convertToMovie())
            checkIsFav(movie.id)
        }
    }

    fun getCredits(movieId: Int) {
        launch {
            when (val response = getMovieCredits.invoke(movieId)) {
                is Either.Right -> _model.value =
                    Event(UiModel.ShowCast(response.b.map { it.convertToCast() }))
            }
        }
    }

    fun movieClicked(cast: Cast) {
        _model.value = Event(UiModel.Navigation(cast))
    }
}