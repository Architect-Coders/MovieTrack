package com.afrasilv.usecases

import com.afrasilv.data.repository.MoviesRepository

class GetMoviesFromPersonId(
    private val moviesRepository: MoviesRepository
) {
    suspend fun invoke(personId: Int) = moviesRepository.getMoviesFromPersonId(personId)
}