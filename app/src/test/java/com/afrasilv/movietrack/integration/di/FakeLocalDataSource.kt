package com.afrasilv.movietrack.integration.di

import arrow.core.extensions.list.foldable.exists
import com.afrasilv.data.source.LocalDataSource
import com.afrasilv.domain.Movie

class FakeLocalDataSource : LocalDataSource {

    private val movieList = mutableListOf<Movie>()

    override suspend fun getFavoriteMovies(): List<Movie> {
        return movieList
    }

    override suspend fun saveMovies(popularMovies: List<Movie>) {
        movieList.addAll(popularMovies)
    }

    override suspend fun checkIfMovieIsFav(movieId: Int): Boolean {
        return movieList.exists { it.id == movieId }
    }

    override suspend fun removeIfFav(movie: Movie) {
        if (checkIfMovieIsFav(movie.id)) {
            movieList.remove(movie)
        } else {
            movieList.add(movie)
        }
     }

    override suspend fun isEmpty(): Boolean {
        return movieList.isEmpty()
    }
}