package com.afrasilv.data.repository

import com.afrasilv.data.source.LocalDataSource
import com.afrasilv.domain.Movie

object CommonRepository {
    suspend fun updateMovieListWithFavData(localDataSource: LocalDataSource, movieList: List<Movie>): List<Movie> {
        localDataSource.getFavoriteMovies().forEach { movieFav ->
            movieList.find { it.id == movieFav.id }?.apply { isFavorite = true }
        }

        return movieList
    }
}