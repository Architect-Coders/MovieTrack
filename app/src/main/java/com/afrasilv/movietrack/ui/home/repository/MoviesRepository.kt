package com.afrasilv.movietrack.ui.home.repository

import arrow.core.Either
import com.afrasilv.movietrack.SERVICE_API_KEY
import com.afrasilv.movietrack.retrofit.RetrofitAPI
import com.afrasilv.movietrack.ui.base.ErrorType
import com.afrasilv.movietrack.ui.base.Failure
import com.afrasilv.movietrack.ui.base.FailureModel
import com.afrasilv.movietrack.ui.home.model.BaseResponse

object MoviesRepository {

    suspend fun discoverMoviesByPopularity(): Either<Failure, BaseResponse> {
        return try {
            val response = RetrofitAPI.service.discoverMoviesByPopularityAsync(SERVICE_API_KEY)
            if (response.isSuccessful && response.body() != null) {
                val product = response.body()!!
                Either.right(product)
            } else
                Either.left(Failure(FailureModel("", "", "", ErrorType.SERVICE_ERROR)))
        } catch (e: Exception) {
            Either.left(Failure(FailureModel("", "", "", ErrorType.SERVICE_ERROR)))
        }
    }
}