package com.afrasilv.movietrack.ui.details.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cast(
    val cast_id: Int,
    val character: String,
    val creditId: String,
    val gender: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val profilePath: String?
) : Parcelable