package com.afrasilv.movietrack.ui.details.repository

import arrow.core.Either
import com.afrasilv.movietrack.SERVICE_API_KEY
import com.afrasilv.movietrack.retrofit.RetrofitAPI
import com.afrasilv.movietrack.ui.base.ErrorType
import com.afrasilv.movietrack.ui.base.Failure
import com.afrasilv.movietrack.ui.base.FailureModel
import com.afrasilv.movietrack.ui.details.model.Cast

object CreditsRepository {

    suspend fun discoverMoviesByPopularity(movieId: Int): Either<Failure, List<Cast>> {
        return try {
            val response = RetrofitAPI.service.getMovieCredits(movieId, SERVICE_API_KEY)
            if (response.isSuccessful && response.body() != null) {
                val credits = response.body()!!
                Either.right(credits.cast)
            } else
                Either.left(Failure(FailureModel("", "", "", ErrorType.SERVICE_ERROR)))
        } catch (e: Exception) {
            Either.left(Failure(FailureModel("", "", "", ErrorType.SERVICE_ERROR)))
        }
    }
}