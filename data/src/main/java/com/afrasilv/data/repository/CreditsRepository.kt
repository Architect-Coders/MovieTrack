package com.afrasilv.data.repository

import arrow.core.Either
import com.afrasilv.data.source.LocalDataSource
import com.afrasilv.data.source.RemoteDataSource
import com.afrasilv.domain.CastRemote
import com.afrasilv.domain.ErrorType
import com.afrasilv.domain.Failure
import com.afrasilv.domain.FailureModel

class CreditsRepository(
    private val remoteDataSource: RemoteDataSource,
    private val apiKey: String
) {
    suspend fun getMovieCredits(movieId: Int): Either<Failure, List<CastRemote>> {
        return try {
            remoteDataSource.getMovieCredits(apiKey, movieId)
        } catch (e: Exception) {
            Either.left(Failure(FailureModel("", "", "", ErrorType.SERVICE_ERROR)))
        }
    }
}