package com.afrasilv.movietrack.ui.castdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import arrow.core.Either
import com.afrasilv.movietrack.Event
import com.afrasilv.movietrack.ui.base.BaseViewModel
import com.afrasilv.movietrack.ui.castdetails.repository.CastRepository
import com.afrasilv.movietrack.ui.home.model.MovieInfo
import com.afrasilv.movietrack.ui.home.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CastViewModel(private val moviesRepository: MoviesRepository) : BaseViewModel() {

    private val _model = MutableLiveData<Event<UiModel>>()
    val model: LiveData<Event<UiModel>>
        get() = _model

    sealed class UiModel {
        object Loading : UiModel()
        class ShowMovies(val movieList: List<MovieInfo>) : UiModel()
        class Navigation(val movie: MovieInfo) : UiModel()
    }

    fun getMoviesByCastId(name: String) {
        launch(Dispatchers.IO) {
            when (val response = CastRepository.searchPerson(name)) {
                is Either.Right -> getCastMovieDataById(response.b)
            }
        }
    }

    private fun getCastMovieDataById(personId: Int) {
        launch(Dispatchers.IO) {
            when (val response = moviesRepository.searchPerson(personId)) {
                is Either.Right -> _model.postValue(Event(UiModel.ShowMovies(response.b)))
            }
        }
    }

    fun movieClicked(movie: MovieInfo) {
        _model.value = Event(UiModel.Navigation(movie))
    }
}