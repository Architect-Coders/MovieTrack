package com.afrasilv.data.source

import com.afrasilv.domain.Movie

interface LocalDataSource {
    suspend fun getFavoriteMovies(): List<Movie>
    suspend fun saveMovies(popularMovies: List<Movie>)
    suspend fun checkIfMovieIsFav(movieId: Int): Boolean
    suspend fun removeIfFav(movie: Movie)
    suspend fun isEmpty(): Boolean
}