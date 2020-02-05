package com.afrasilv.usecases

import com.afrasilv.data.repository.MoviesRepository

class CheckIfMovieIsFav(
    private val moviesRepository: MoviesRepository
) {
    suspend fun invoke(movieId: Int) = moviesRepository.checkIfMovieIsFav(movieId)
}