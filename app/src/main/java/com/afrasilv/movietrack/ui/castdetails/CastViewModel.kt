package com.afrasilv.movietrack.ui.castdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import arrow.core.Either
import com.afrasilv.domain.Movie
import com.afrasilv.domain.Person
import com.afrasilv.movietrack.Event
import com.afrasilv.movietrack.ui.base.BaseViewModel
import com.afrasilv.movietrack.ui.base.convertToMovieInfo
import com.afrasilv.movietrack.ui.model.MovieInfo
import com.afrasilv.usecases.GetMoviesFromPersonId
import com.afrasilv.usecases.GetPersonDataById
import com.afrasilv.usecases.SearchPerson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CastViewModel(
    private val searchPerson: SearchPerson,
    private val getPersonDataById: GetPersonDataById,
    private val getMoviesFromPersonId: GetMoviesFromPersonId
) : BaseViewModel() {

    private val _model = MutableLiveData<Event<UiModel>>()
    val model: LiveData<Event<UiModel>>
        get() = _model

    sealed class UiModel {
        object Loading : UiModel()
        class ShowMovies(val movieList: List<MovieInfo>) : UiModel()
        class Navigation(val movie: MovieInfo) : UiModel()
        class PersonData(val person: Person) : UiModel()
    }

    fun getMoviesByCastId(name: String) {
        launch(Dispatchers.IO) {
            when (val response = searchPerson.invoke(name)) {
                is Either.Right -> {
                    getPersonDataByPersonId(response.b)
                    getCastMovieDataById(response.b)
                }
            }
        }
    }

    private fun getPersonDataByPersonId(personId: Int) {
        launch(Dispatchers.IO) {
            when (val response = getPersonDataById.invoke(personId)) {
                is Either.Right -> _model.postValue(Event(UiModel.PersonData(response.b)))
            }
        }
    }


    private fun getCastMovieDataById(personId: Int) {
        launch(Dispatchers.IO) {
            when (val response = getMoviesFromPersonId.invoke(personId)) {
                is Either.Right -> _model.postValue(Event(UiModel.ShowMovies(response.b.map(Movie::convertToMovieInfo))))
            }
        }
    }

    fun movieClicked(movie: MovieInfo) {
        _model.value = Event(UiModel.Navigation(movie))
    }
}