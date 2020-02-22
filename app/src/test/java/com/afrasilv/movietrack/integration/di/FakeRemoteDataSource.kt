package com.afrasilv.movietrack.integration.di

import arrow.core.Either
import com.afrasilv.data.source.RemoteDataSource
import com.afrasilv.domain.CastRemote
import com.afrasilv.domain.Failure
import com.afrasilv.domain.Movie
import com.afrasilv.domain.Person
import com.afrasilv.movietrack.castRemoteList
import com.afrasilv.movietrack.movieList
import com.afrasilv.movietrack.personMock

class FakeRemoteDataSource : RemoteDataSource {
    override suspend fun discoverMoviesByPopularity(
        apiKey: String,
        region: String
    ): Either<Failure, List<Movie>> {
        return Either.right(movieList)
    }

    override suspend fun searchMovieByName(
        apiKey: String,
        name: String
    ): Either<Failure, List<Movie>> {
        return Either.right(movieList)
    }

    override suspend fun getMoviesFromPersonId(
        apiKey: String,
        personId: Int
    ): Either<Failure, List<Movie>> {
        return Either.right(movieList)
    }

    override suspend fun getMovieCredits(
        apiKey: String,
        movieId: Int
    ): Either<Failure, List<CastRemote>> {
        return Either.right(castRemoteList)
    }

    override suspend fun searchPerson(apiKey: String, name: String): Either<Failure, Int> {
        return Either.right(123)
    }

    override suspend fun getPersonDataById(apiKey: String, personId: Int): Either<Failure, Person> {
        return Either.right(personMock)
    }
}