package com.afrasilv.movietrack.data.retrofit

import arrow.core.Either
import com.afrasilv.data.source.RemoteDataSource
import com.afrasilv.domain.*
import com.afrasilv.movietrack.data.convertToMovie
import java.net.URLEncoder

class MovieTrackRemoteDataSource : RemoteDataSource {
    override suspend fun discoverMoviesByPopularity(
        apiKey: String,
        region: String
    ): Either<Failure, List<Movie>> {
        return try {
            val response =
                RetrofitAPI.service.discoverMoviesByPopularityAsync(apiKey, region)
            if (response.isSuccessful && response.body() != null) {
                Either.right(response.body()!!.results.map { it.convertToMovie() })
            } else
                Either.left(Failure(FailureModel("", "", "", ErrorType.SERVICE_ERROR)))
        } catch (e: Exception) {
            Either.left(Failure(FailureModel("", "", "", ErrorType.SERVICE_ERROR)))
        }
    }

    override suspend fun searchMovieByName(
        apiKey: String,
        name: String
    ): Either<Failure, List<Movie>> {
        return try {
            val response = RetrofitAPI.service.searchMovieByName(
                apiKey,
                URLEncoder.encode(name, "UTF-8")
            )
            if (response.isSuccessful && response.body() != null) {
                Either.right(response.body()!!.results.map { it.convertToMovie() })
            } else
                Either.left(Failure(FailureModel("", "", "", ErrorType.SERVICE_ERROR)))
        } catch (e: Exception) {
            Either.left(Failure(FailureModel("", "", "", ErrorType.SERVICE_ERROR)))
        }
    }

    override suspend fun getMoviesFromPersonId(
        apiKey: String,
        personId: Int
    ): Either<Failure, List<Movie>> {
        return try {
            val response = RetrofitAPI.service.getMoviesFromPersonId(
                personId,
                apiKey
            )
            if (response.isSuccessful && response.body() != null) {
                val searchedList = response.body()!!
                Either.right(searchedList.cast)
            } else
                Either.left(Failure(FailureModel("", "", "", ErrorType.SERVICE_ERROR)))
        } catch (e: Exception) {
            Either.left(Failure(FailureModel("", "", "", ErrorType.SERVICE_ERROR)))
        }
    }

    override suspend fun getMovieCredits(
        apiKey: String,
        movieId: Int
    ): Either<Failure, List<CastRemote>> {
        return try {
            val response = RetrofitAPI.service.getMovieCredits(
                movieId,
                apiKey
            )
            if (response.isSuccessful && response.body() != null) {
                val credits = response.body()!!
                Either.right(credits.cast)
            } else
                Either.left(Failure(FailureModel("", "", "", ErrorType.SERVICE_ERROR)))
        } catch (e: Exception) {
            Either.left(Failure(FailureModel("", "", "", ErrorType.SERVICE_ERROR)))
        }
    }

    override suspend fun searchPerson(apiKey: String, name: String): Either<Failure, Int> {
        return try {
            val response = RetrofitAPI.service.searchPerson(
                apiKey,
                URLEncoder.encode(name, "UTF-8")
            )
            if (response.isSuccessful && response.body() != null) {
                val castSearch = response.body()!!
                if (castSearch.results.isNotEmpty()) {
                    Either.right(castSearch.results[0].id)
                } else {
                    Either.left(Failure(FailureModel("", "", "", ErrorType.SERVICE_ERROR)))
                }
            } else
                Either.left(Failure(FailureModel("", "", "", ErrorType.SERVICE_ERROR)))
        } catch (e: Exception) {
            Either.left(Failure(FailureModel("", "", "", ErrorType.SERVICE_ERROR)))
        }
    }

    override suspend fun getPersonDataById(
        apiKey: String,
        personId: Int
    ): Either<Failure, Person> {
        return try {
            val response = RetrofitAPI.service.getPersonData(
                personId,
                apiKey
            )
            if (response.isSuccessful && response.body() != null) {
                var person: Person? = null
                response.body()?.let {
                    person = it
                }
                if (person != null) {
                    Either.right(person!!)
                } else {
                    Either.left(Failure(FailureModel("", "", "", ErrorType.SERVICE_ERROR)))
                }
            } else
                Either.left(Failure(FailureModel("", "", "", ErrorType.SERVICE_ERROR)))
        } catch (e: Exception) {
            Either.left(Failure(FailureModel("", "", "", ErrorType.SERVICE_ERROR)))
        }
    }

}
