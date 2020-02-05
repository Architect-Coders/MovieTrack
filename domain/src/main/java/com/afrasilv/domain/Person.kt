package com.afrasilv.domain

import com.google.gson.annotations.SerializedName

data class Person (
    val birthday : String?,
    val deathday : String?,
    val id : Int,
    val name : String,
    val gender : Int,
    val biography : String,
    val popularity : Double,
    @SerializedName("place_of_birth")
    val placeOfBirth : String,
    @SerializedName("profilePath")
    val profilePath : String,
    val adult : Boolean,
    @SerializedName("imdb_id")
    val imdbId : String,
    val homepage : String?
)