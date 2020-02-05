package com.afrasilv.usecases

import com.afrasilv.data.repository.CastRepository

class SearchPerson(
    private val castRepository: CastRepository
) {
    suspend fun invoke(name: String) = castRepository.searchPerson(name)
}