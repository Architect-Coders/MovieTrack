package com.afrasilv.movietrack.ui.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieInfo(
    val id: Int,
    val title: String,
    val popularity: Double,
    @SerializedName("vote_count") val voteCount: Int? = null,
    val video: Boolean? = null,
    @SerializedName("poster_path") val posterPath: String,
    val adult: Boolean? = null,
    @SerializedName("backdrop_path") val backdropPath: String? = null,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("genre_ids") val genreIds: List<Int>? = null,
    @SerializedName("vote_average") val voteAverage: Double,
    val overview: String,
    @SerializedName("release_date") val releaseDate: String?,
    var isFavorite: Boolean,
    var character: String?
) : Parcelable