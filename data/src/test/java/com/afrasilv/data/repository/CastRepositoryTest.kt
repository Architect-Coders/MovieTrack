package com.afrasilv.data.repository

import arrow.core.Either
import com.afrasilv.data.source.RemoteDataSource
import com.afrasilv.domain.Person
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CastRepositoryTest {

    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    lateinit var castRepository: CastRepository

    private val apiKey = "qwerty"

    @Before
    fun setUp() {
        castRepository =
            CastRepository(remoteDataSource, apiKey)
    }

    @Test
    fun `searchPerson return a valid personId`() {
        runBlocking {
            val personId = 123
            whenever(remoteDataSource.searchPerson(apiKey, "")).thenReturn(Either.right(personId))

            val result = castRepository.searchPerson("")

            assert(result.isRight())
            result as Either.Right
            assertEquals(personId, result.b)
        }
    }

    @Test
    fun `getPersonDataById return a valid Person value`() {
        runBlocking {
            val personId = 123
            val personMock  = mock<Person>()
            whenever(remoteDataSource.getPersonDataById(apiKey, personId)).thenReturn(Either.right(personMock))

            val result = castRepository.getPersonDataById(personId)

            assert(result.isRight())
            result as Either.Right
            assertEquals(personMock, result.b)
        }
    }
}