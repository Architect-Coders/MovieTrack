package com.afrasilv.usecases

import com.afrasilv.data.repository.CastRepository

class GetPersonDataById(
    private val castRepository: CastRepository
) {
    suspend fun invoke(personId: Int) = castRepository.getPersonDataById(personId)
}