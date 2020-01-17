package com.afrasilv.movietrack.ui.castdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import arrow.core.Either
import com.afrasilv.movietrack.ui.base.BaseViewModel
import com.afrasilv.movietrack.ui.castdetails.repository.CastRepository
import com.afrasilv.movietrack.ui.details.DetailsViewModel
import com.afrasilv.movietrack.ui.details.repository.CreditsRepository
import com.afrasilv.movietrack.ui.home.HomeViewModel
import com.afrasilv.movietrack.ui.home.model.MovieInfo
import com.afrasilv.movietrack.ui.home.repository.MoviesRepository
import kotlinx.coroutines.launch

class CastViewModel(private val moviesRepository: MoviesRepository) : BaseViewModel() {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() = _model

    sealed class UiModel {
        object Loading : UiModel()
        class ShowMovies(val movieList: List<MovieInfo>) : UiModel()
    }

    fun getMoviesByCastId(name: String) {
        launch {
            when (val response = CastRepository.searchPerson(name)) {
                is Either.Right -> getCastMovieDataById(response.b)
            }
        }
    }

    private fun getCastMovieDataById(personId: Int) {
        launch {
            when (val response = moviesRepository.searchPerson(personId)) {
                is Either.Right -> _model.postValue(UiModel.ShowMovies(response.b))
            }
        }
    }

    fun movieClicked(movie: MovieInfo) {
//        _model.value = HomeViewModel.UiModel.Navigation(movie)
    }
}