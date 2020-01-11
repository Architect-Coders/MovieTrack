package com.afrasilv.movietrack.ui.home.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieInfo(
    val id : Int,
    val title : String,
    val popularity : Double,
    @SerializedName("vote_count") val voteCount : Int,
    val video : Boolean,
    @SerializedName("poster_path") val posterPath : String,
    val adult : Boolean,
    @SerializedName("backdrop_path") val backdropPath : String?,
    @SerializedName("original_language") val originalLanguage : String,
    @SerializedName("original_title") val originalTitle : String,
    @SerializedName("genre_ids") val genreIds : List<Int>,
    @SerializedName("vote_average") val voteAverage : Double,
    val overview : String,
    @SerializedName("release_date") val release_date : String
) : Parcelable