package com.afrasilv.movietrack.ui.home.repository

import arrow.core.Either
import com.afrasilv.movietrack.SERVICE_API_KEY
import com.afrasilv.movietrack.retrofit.RetrofitAPI
import com.afrasilv.movietrack.ui.base.ErrorType
import com.afrasilv.movietrack.ui.base.Failure
import com.afrasilv.movietrack.ui.base.FailureModel
import com.afrasilv.movietrack.ui.home.model.BaseResponse
import java.net.URLEncoder

object MoviesRepository {

    suspend fun discoverMoviesByPopularity(): Either<Failure, BaseResponse> {
        return try {
            val response = RetrofitAPI.service.discoverMoviesByPopularityAsync(SERVICE_API_KEY)
            if (response.isSuccessful && response.body() != null) {
                val movieList = response.body()!!
                Either.right(movieList)
            } else
                Either.left(Failure(FailureModel("", "", "", ErrorType.SERVICE_ERROR)))
        } catch (e: Exception) {
            Either.left(Failure(FailureModel("", "", "", ErrorType.SERVICE_ERROR)))
        }
    }

    suspend fun searchMoviesByName(name: String): Either<Failure, BaseResponse> {
        return try {
            val response = RetrofitAPI.service.searchMovieByName(SERVICE_API_KEY, URLEncoder.encode(name, "UTF-8"))
            if (response.isSuccessful && response.body() != null) {
                val searchedList = response.body()!!
                Either.right(searchedList)
            } else
                Either.left(Failure(FailureModel("", "", "", ErrorType.SERVICE_ERROR)))
        } catch (e: Exception) {
            Either.left(Failure(FailureModel("", "", "", ErrorType.SERVICE_ERROR)))
        }
    }
}