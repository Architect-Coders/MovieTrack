package com.afrasilv.data.repository

import arrow.core.Either
import com.afrasilv.data.source.RemoteDataSource
import com.afrasilv.domain.Failure
import com.afrasilv.domain.Person

class CastRepository (
    private val remoteDataSource: RemoteDataSource,
    private val apiKey: String
) {
    suspend fun searchPerson(name: String): Either<Failure, Int> = remoteDataSource.searchPerson(apiKey, name)

    suspend fun getPersonDataById(personId: Int): Either<Failure, Person> = remoteDataSource.getPersonDataById(apiKey, personId)
}