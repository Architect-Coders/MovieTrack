package com.afrasilv.domain

import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Int,
    val title: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    val overview: String,
    @SerializedName("release_date")
    val releaseDate: String?,
    var isFavorite: Boolean = false,
    var character: String? = null
)