package com.afrasilv.usecases

import com.afrasilv.data.repository.MoviesRepository

class SearchMoviesByName(
    private val moviesRepository: MoviesRepository
) {
    suspend fun invoke(name: String) = moviesRepository.searchMoviesByName(name)
}