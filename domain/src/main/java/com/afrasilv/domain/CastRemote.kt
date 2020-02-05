package com.afrasilv.domain

import com.google.gson.annotations.SerializedName

data class CreditsResponse(
    val id: Int,
    val cast: List<CastRemote>
)

data class CastRemote(
    @SerializedName("cast_id")
    val cast_id: Int,
    val character: String,
    @SerializedName("credit_id")
    val creditId: String,
    val gender: Int,
    val id: Int,
    val name: String,
    val order: Int,
    @SerializedName("profile_path")
    val profilePath: String?
)