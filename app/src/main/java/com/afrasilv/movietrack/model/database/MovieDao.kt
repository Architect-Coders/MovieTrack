package com.afrasilv.movietrack.model.database

import androidx.room.*

@Dao
interface MovieDao {

    @Query("SELECT * FROM MovieFavDb")
    fun getAll(): List<MovieFavDb>

    @Query("SELECT * FROM MovieFavDb WHERE id = :id")
    fun findById(id: Int): MovieFavDb?

    @Query("SELECT COUNT(id) FROM MovieFavDb")
    fun movieCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovies(movies: List<MovieFavDb>)

    @Update
    fun updateMovie(movie: MovieFavDb)

    @Delete
    fun removeMovie(movie: MovieFavDb)
}