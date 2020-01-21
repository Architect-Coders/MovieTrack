package com.afrasilv.movietrack.ui.home.repository

import arrow.core.Either
import com.afrasilv.movietrack.MovieTrackApp
import com.afrasilv.movietrack.SERVICE_API_KEY
import com.afrasilv.movietrack.model.database.MovieFavDb
import com.afrasilv.movietrack.retrofit.RetrofitAPI
import com.afrasilv.movietrack.ui.base.ErrorType
import com.afrasilv.movietrack.ui.base.Failure
import com.afrasilv.movietrack.ui.base.FailureModel
import com.afrasilv.movietrack.ui.home.model.MovieInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URLEncoder

class MoviesRepository(private val application: MovieTrackApp) {

    suspend fun discoverMoviesByPopularity(region: String): Either<Failure, List<MovieInfo>> {
        return try {
            val response = RetrofitAPI.service.discoverMoviesByPopularityAsync(SERVICE_API_KEY, region)
            if (response.isSuccessful && response.body() != null) {
                val movieList = response.body()!!
                Either.right(updateMovieListWithFavData(movieList.results))
            } else
                Either.left(Failure(FailureModel("", "", "", ErrorType.SERVICE_ERROR)))
        } catch (e: Exception) {
            Either.left(Failure(FailureModel("", "", "", ErrorType.SERVICE_ERROR)))
        }
    }

    suspend fun searchMoviesByName(name: String): Either<Failure, List<MovieInfo>> {
        return try {
            val response = RetrofitAPI.service.searchMovieByName(
                SERVICE_API_KEY,
                URLEncoder.encode(name, "UTF-8")
            )
            if (response.isSuccessful && response.body() != null) {
                val searchedList = response.body()!!
                Either.right(updateMovieListWithFavData(searchedList.results))
            } else
                Either.left(Failure(FailureModel("", "", "", ErrorType.SERVICE_ERROR)))
        } catch (e: Exception) {
            Either.left(Failure(FailureModel("", "", "", ErrorType.SERVICE_ERROR)))
        }
    }

    private suspend fun updateMovieListWithFavData(movieList: List<MovieInfo>): List<MovieInfo> =
        withContext(Dispatchers.IO) {
            application.db.movieDao().getAll().forEach { movieFav ->
                movieList.find { it.id == movieFav.id }?.apply { isFavorite = true }
            }

            movieList
        }


    suspend fun getFavoriteMovies(): Either<Failure, List<MovieInfo>> {
        val dataList = application.db.movieDao().getAll()
        return if (dataList.isNotEmpty()) {
            Either.right(dataList.map(MovieFavDb::convertToMovieInfo))
        } else {
            Either.left(Failure(FailureModel("", "", "", ErrorType.SERVICE_ERROR)))
        }
    }

    suspend fun checkIfMovieIsFav(movieId: Int): Either<Failure, Boolean> =
        withContext(Dispatchers.IO) {
            Either.right(application.db.movieDao().findById(movieId) != null)
        }

    suspend fun removeIfFav(movie: MovieInfo) = withContext(Dispatchers.IO) {
        val movieDb = movie.convertToDbMovie()
        val either = checkIfMovieIsFav(movie.id)
        if ((either as Either.Right).b) {
            application.db.movieDao().removeMovie(movieDb)
        } else {
            application.db.movieDao().insertMovies(listOf(movieDb))
        }
    }

    suspend fun searchPerson(personId: Int): Either<Failure, List<MovieInfo>> {
        return try {
            val response = RetrofitAPI.service.getMoviesFromPersonId(
                personId,
                SERVICE_API_KEY
            )
            if (response.isSuccessful && response.body() != null) {
                val searchedList = response.body()!!
                Either.right(updateMovieListWithFavData(searchedList.cast))
            } else
                Either.left(Failure(FailureModel("", "", "", ErrorType.SERVICE_ERROR)))
        } catch (e: Exception) {
            Either.left(Failure(FailureModel("", "", "", ErrorType.SERVICE_ERROR)))
        }
    }
}

private fun MovieInfo.convertToDbMovie() = MovieFavDb(
    id,
    title,
    overview,
    popularity,
    posterPath,
    backdropPath ?: posterPath,
    releaseDate,
    originalLanguage,
    originalTitle,
    voteAverage
)

private fun MovieFavDb.convertToMovieInfo() = MovieInfo(
    id = id,
    title = title,
    popularity = popularity,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    releaseDate = releaseDate,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    voteAverage = voteAverage,
    isFavorite = true
)