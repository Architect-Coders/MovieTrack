package com.afrasilv.data.repository

import arrow.core.Either
import com.afrasilv.data.repository.CommonRepository.updateMovieListWithFavData
import com.afrasilv.data.source.LocalDataSource
import com.afrasilv.data.source.RemoteDataSource
import com.afrasilv.domain.ErrorType
import com.afrasilv.domain.Failure
import com.afrasilv.domain.FailureModel
import com.afrasilv.domain.Movie

class MoviesRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val regionRepository: LocationRepository,
    private val apiKey: String
) {

    suspend fun discoverMoviesByPopularity(): Either<Failure, List<Movie>> {
        return try {
            val response = remoteDataSource.discoverMoviesByPopularity(apiKey, regionRepository.findLastRegion())
            if (response.isRight()) {
                val movieList = (response as Either.Right).b
                Either.right(updateMovieListWithFavData(localDataSource, movieList))
            } else
                response
        } catch (e: Exception) {
            Either.left(Failure(FailureModel("", "", "", ErrorType.SERVICE_ERROR)))
        }
    }

    suspend fun searchMoviesByName(name: String): Either<Failure, List<Movie>> {
        return try {
            val response = remoteDataSource.searchMovieByName(apiKey, name)
            if (response.isRight()) {
                val movieList = (response as Either.Right).b
                Either.right(updateMovieListWithFavData(localDataSource, movieList))
            } else
                response
        } catch (e: Exception) {
            Either.left(Failure(FailureModel("", "", "", ErrorType.SERVICE_ERROR)))
        }
    }

    suspend fun getFavoriteMovies(): Either<Failure, List<Movie>> {
        val dataList = localDataSource.getFavoriteMovies()
        return if (dataList.isNotEmpty()) {
            Either.right(dataList)
        } else {
            Either.left(Failure(FailureModel("", "", "", ErrorType.SERVICE_ERROR)))
        }
    }

    suspend fun checkIfMovieIsFav(movieId: Int): Boolean =
        localDataSource.checkIfMovieIsFav(movieId)

    suspend fun removeIfFav(movie: Movie) {
        localDataSource.removeIfFav(movie)
    }

    suspend fun getMoviesFromPersonId(personId: Int): Either<Failure, List<Movie>> {
        return try {
            val response = remoteDataSource.getMoviesFromPersonId(apiKey, personId)
            if (response.isRight()) {
                val searchedList = (response as Either.Right).b
                Either.right(updateMovieListWithFavData(localDataSource, searchedList))
            } else
                response
        } catch (e: Exception) {
            Either.left(Failure(FailureModel("", "", "", ErrorType.SERVICE_ERROR)))
        }
    }
}