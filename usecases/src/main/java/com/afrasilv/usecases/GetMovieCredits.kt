package com.afrasilv.usecases

import com.afrasilv.data.repository.CreditsRepository

class GetMovieCredits(
    private val creditsRepository: CreditsRepository
) {
    suspend fun invoke(movieId: Int) = creditsRepository.getMovieCredits(movieId)
}