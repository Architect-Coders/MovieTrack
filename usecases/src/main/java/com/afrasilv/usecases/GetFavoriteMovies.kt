package com.afrasilv.usecases

import com.afrasilv.data.repository.MoviesRepository

class GetFavoriteMovies (
    private val moviesRepository: MoviesRepository
) {
    suspend fun invoke() = moviesRepository.getFavoriteMovies()
}