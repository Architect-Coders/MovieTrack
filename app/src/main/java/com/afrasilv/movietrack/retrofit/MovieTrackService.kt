package com.afrasilv.movietrack.retrofit

import com.afrasilv.movietrack.ui.castdetails.model.CastMovie
import com.afrasilv.movietrack.ui.castdetails.model.CastSearchBase
import com.afrasilv.movietrack.ui.castdetails.model.Person
import com.afrasilv.movietrack.ui.details.model.CreditsResponse
import com.afrasilv.movietrack.ui.home.model.BaseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieTrackService {

    @GET("discover/movie?sort_by=popularity.desc")
    suspend fun discoverMoviesByPopularityAsync(
        @Query("api_key") apiKey: String,
        @Query("region") region: String
    ): Response<BaseResponse>

    @GET("search/movie")
    suspend fun searchMovieByName(
        @Query("api_key") apiKey: String,
        @Query("query") name: String
    ): Response<BaseResponse>

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<CreditsResponse>

    @GET("search/person")
    suspend fun searchPerson(
        @Query("api_key") apiKey: String,
        @Query("query") name: String
    ): Response<CastSearchBase>

    @GET("person/{person_id}")
    suspend fun getPersonData(
        @Path("person_id") personId: Int,
        @Query("api_key") apiKey: String
    ): Response<Person>

    @GET("person/{person_id}/movie_credits")
    suspend fun getMoviesFromPersonId(
        @Path("person_id") personId: Int,
        @Query("api_key") apiKey: String
    ): Response<CastMovie>
}