package com.afrasilv.movietrack.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieFavDb(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val backdropPath: String,
    val releaseDate: String,
    val originalLanguage: String,
    val originalTitle: String,
    val voteAverage: Double
)