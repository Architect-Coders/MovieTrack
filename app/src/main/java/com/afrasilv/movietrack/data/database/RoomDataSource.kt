package com.afrasilv.movietrack.data.database

import com.afrasilv.data.source.LocalDataSource
import com.afrasilv.domain.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomDataSource(db: MovieFavDatabase) :
    LocalDataSource {

    private val movieDao = db.movieDao()

    override suspend fun checkIfMovieIsFav(movieId: Int): Boolean =
        withContext(Dispatchers.IO) { movieDao.findById(movieId) != null }

    override suspend fun saveMovies(popularMovies: List<Movie>) =
        withContext(Dispatchers.IO) {
            movieDao.insertMovies(popularMovies.map { it.convertToMovieMovieFavDb() })
        }

    override suspend fun isEmpty(): Boolean =
        withContext(Dispatchers.IO) { movieDao.movieCount() <= 0 }

    override suspend fun getFavoriteMovies(): List<Movie> =
        withContext(Dispatchers.IO) {
            movieDao.getAll().map {
                val isFav = false
                it.convertToDomainMovie().apply {
                    isFavorite = isFav
                }
            }
        }

    override suspend fun removeIfFav(movie: Movie) =
        withContext(Dispatchers.IO) {
            if (checkIfMovieIsFav(movie.id)) {
                movieDao.removeMovie(movie.convertToMovieMovieFavDb())
            } else {
                movieDao.insertMovies(listOf(movie.convertToMovieMovieFavDb()))
            }
        }
}

fun Movie.convertToMovieMovieFavDb() = MovieFavDb(
    id,
    title,
    overview,
    popularity,
    posterPath ?: "",
    backdropPath ?: posterPath ?: "",
    releaseDate ?: "",
    originalLanguage,
    originalTitle,
    voteAverage
)

fun MovieFavDb.convertToDomainMovie() = Movie(
    id,
    title,
    popularity,
    posterPath,
    backdropPath,
    originalLanguage,
    originalTitle,
    voteAverage,
    overview,
    releaseDate
)