package com.afrasilv.movietrack.ui.castdetails.repository

import arrow.core.Either
import com.afrasilv.movietrack.SERVICE_API_KEY
import com.afrasilv.movietrack.retrofit.RetrofitAPI
import com.afrasilv.movietrack.ui.base.ErrorType
import com.afrasilv.movietrack.ui.base.Failure
import com.afrasilv.movietrack.ui.base.FailureModel
import com.afrasilv.movietrack.ui.castdetails.model.Person
import java.net.URLEncoder

object CastRepository {

    suspend fun searchPerson(name: String): Either<Failure, Int> {
        return try {
            val response = RetrofitAPI.service.searchPerson(
                SERVICE_API_KEY,
                URLEncoder.encode(name, "UTF-8")
            )
            if (response.isSuccessful && response.body() != null) {
                val castSearch = response.body()!!
                if (castSearch.results.isNotEmpty()) {
                    Either.right(castSearch.results[0].id)
                } else {
                    Either.left(Failure(FailureModel("", "", "", ErrorType.SERVICE_ERROR)))
                }
            } else
                Either.left(Failure(FailureModel("", "", "", ErrorType.SERVICE_ERROR)))
        } catch (e: Exception) {
            Either.left(Failure(FailureModel("", "", "", ErrorType.SERVICE_ERROR)))
        }
    }

    suspend fun getPersonDataById(personId: Int): Either<Failure, Person> {
        return try {
            val response = RetrofitAPI.service.getPersonData(personId, SERVICE_API_KEY)
            if (response.isSuccessful && response.body() != null) {
                var person: Person? = null
                response.body()?.let {
                    person = it
                }
                if (person != null) {
                    Either.right(person!!)
                } else {
                    Either.left(Failure(FailureModel("", "", "", ErrorType.SERVICE_ERROR)))
                }
            } else
                Either.left(Failure(FailureModel("", "", "", ErrorType.SERVICE_ERROR)))
        } catch (e: Exception) {
            Either.left(Failure(FailureModel("", "", "", ErrorType.SERVICE_ERROR)))
        }
    }
}