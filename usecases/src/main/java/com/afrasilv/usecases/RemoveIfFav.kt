package com.afrasilv.usecases

import com.afrasilv.data.repository.MoviesRepository
import com.afrasilv.domain.Movie

class RemoveIfFav(
    private val moviesRepository: MoviesRepository
) {
    suspend fun invoke(movie: Movie) = moviesRepository.removeIfFav(movie)
}