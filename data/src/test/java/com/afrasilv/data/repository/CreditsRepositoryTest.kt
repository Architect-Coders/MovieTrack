package com.afrasilv.data.repository

import arrow.core.Either
import com.afrasilv.data.source.RemoteDataSource
import com.afrasilv.domain.CastRemote
import com.afrasilv.domain.ErrorType
import com.afrasilv.domain.Failure
import com.afrasilv.domain.FailureModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CreditsRepositoryTest {

    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    lateinit var creditsRepository: CreditsRepository

    private val apiKey = "qwerty"

    @Before
    fun setUp() {
        creditsRepository =
            CreditsRepository(remoteDataSource, apiKey)
    }

    @Test
    fun `getMovieCredits return a valid list`() {
        runBlocking {
            val listCastRemoteMock  = mock<List<CastRemote>>()
            whenever(remoteDataSource.getMovieCredits(apiKey, 12)).thenReturn(Either.right(listCastRemoteMock))

            val result = creditsRepository.getMovieCredits(12)

            assert(result.isRight())
            result as Either.Right
            assertEquals(listCastRemoteMock, result.b)
        }
    }

    @Test
    fun `getMovieCredits return an error list`() {
        runBlocking {
            val failureModel = FailureModel("", "", "", ErrorType.SERVICE_ERROR)
            whenever(remoteDataSource.getMovieCredits(apiKey, 12)).thenReturn(Either.left(Failure(failureModel)))

            val result = creditsRepository.getMovieCredits(12)

            assert(result.isLeft())
            result as Either.Left
            assertEquals(failureModel, result.a.model)
        }
    }
}