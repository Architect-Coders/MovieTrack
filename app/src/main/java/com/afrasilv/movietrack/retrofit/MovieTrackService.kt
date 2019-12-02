package com.afrasilv.movietrack.retrofit

import com.afrasilv.movietrack.ui.home.model.BaseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieTrackService {

    @GET("discover/movie?sort_by=popularity.desc")
    suspend fun discoverMoviesByPopularityAsync (
        @Query("api_key") apiKey: String): Response<BaseResponse>
}