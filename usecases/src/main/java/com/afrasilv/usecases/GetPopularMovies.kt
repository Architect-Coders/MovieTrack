package com.afrasilv.usecases

import com.afrasilv.data.repository.MoviesRepository

class GetPopularMovies(
    private val moviesRepository: MoviesRepository
) {
    suspend fun invoke() = moviesRepository.discoverMoviesByPopularity()
}