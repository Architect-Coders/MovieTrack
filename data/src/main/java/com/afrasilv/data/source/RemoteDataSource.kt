package com.afrasilv.data.source

import arrow.core.Either
import com.afrasilv.domain.CastRemote
import com.afrasilv.domain.Failure
import com.afrasilv.domain.Movie
import com.afrasilv.domain.Person

interface RemoteDataSource {
    suspend fun discoverMoviesByPopularity(apiKey: String, region: String): Either<Failure, List<Movie>>
    suspend fun searchMovieByName(apiKey: String, name: String): Either<Failure, List<Movie>>
    suspend fun getMoviesFromPersonId(apiKey: String, personId: Int): Either<Failure, List<Movie>>
    suspend fun getMovieCredits(apiKey: String, movieId: Int): Either<Failure, List<CastRemote>>
    suspend fun searchPerson(apiKey: String, name: String): Either<Failure, Int>
    suspend fun getPersonDataById(apiKey: String, personId: Int): Either<Failure, Person>
}