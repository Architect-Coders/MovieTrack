package com.afrasilv.data.repository

import arrow.core.Either
import arrow.core.extensions.list.functorFilter.filter
import com.afrasilv.data.source.LocalDataSource
import com.afrasilv.data.source.RemoteDataSource
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MoviesRepositoryTest {

    @Mock
    lateinit var localDataSource: LocalDataSource

    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    @Mock
    lateinit var locationRepository: LocationRepository

    lateinit var moviesRepository: MoviesRepository

    private val apiKey = "qwerty"

    @Before
    fun setUp() {
        runBlocking {
            whenever(locationRepository.findLastRegion()).thenReturn("ES")
        }

        moviesRepository =
            MoviesRepository(localDataSource, remoteDataSource, locationRepository, apiKey)
    }

    @Test
    fun `discoverMoviesByPopularity with any fav data`() {
        runBlocking {

            val movieMockList = listOf(
                movieMock.copy(id = 0),
                movieMock.copy(id = 1),
                movieMock.copy(id = 2),
                movieMock.copy(id = 3)
            )

            whenever(remoteDataSource.discoverMoviesByPopularity(apiKey, "ES")).thenReturn(
                Either.right(
                    movieMockList
                )
            )
            whenever(localDataSource.getFavoriteMovies()).thenReturn(emptyList())

            val result = moviesRepository.discoverMoviesByPopularity()

            assert(result.isRight())
            result as Either.Right
            assertEquals(movieMockList, result.b)
        }
    }

    @Test
    fun `discoverMoviesByPopularity with fav data`() {
        runBlocking {

            val movieMockList = listOf(
                movieMock.copy(id = 0),
                movieMock.copy(id = 1),
                movieMock.copy(id = 2),
                movieMock.copy(id = 3)
            )

            val favMoviesMockList = listOf(
                movieMock.copy(id = 7),
                movieMock.copy(id = 2),
                movieMock.copy(id = 99)
            )

            whenever(remoteDataSource.discoverMoviesByPopularity(apiKey, "ES")).thenReturn(
                Either.right(
                    movieMockList
                )
            )
            whenever(localDataSource.getFavoriteMovies()).thenReturn(favMoviesMockList)

            val result = moviesRepository.discoverMoviesByPopularity()

            assert(result.isRight())
            result as Either.Right
            assertEquals(movieMockList.size, result.b.size)
            assert(result.b.find { it.id == 2 }!!.isFavorite)
            assert(result.b.filter { it.isFavorite }.size == 1)
        }
    }

    @Test
    fun `searchMoviesByName with any fav data`() {
        runBlocking {
            val movieMockList = listOf(
                movieMock.copy(id = 0),
                movieMock.copy(id = 1)
            )

            whenever(localDataSource.getFavoriteMovies()).thenReturn(emptyList())
            whenever(remoteDataSource.searchMovieByName(apiKey, "name")).thenReturn(
                Either.right(
                    movieMockList
                )
            )

            val result = moviesRepository.searchMoviesByName("name")

            assert(result.isRight())
            result as Either.Right
            assertEquals(movieMockList, result.b)
        }
    }

    @Test
    fun `getFavoriteMovies with any data`() {
        runBlocking {
            whenever(localDataSource.getFavoriteMovies()).thenReturn(emptyList())
            val result = moviesRepository.getFavoriteMovies()

            assert(result.isLeft())
        }
    }

    @Test
    fun `getFavoriteMovies with data`() {
        runBlocking {
            val movieMockList = listOf(
                movieMock.copy(id = 0),
                movieMock.copy(id = 1)
            )

            whenever(localDataSource.getFavoriteMovies()).thenReturn(movieMockList)
            val result = moviesRepository.getFavoriteMovies()

            assert(result.isRight())
            result as Either.Right
            assertEquals(movieMockList, result.b)
        }
    }

    @Test
    fun `getMoviesFromPersonId with any fav data`() {
        runBlocking {

            val movieMockList = listOf(
                movieMock.copy(id = 0),
                movieMock.copy(id = 1),
                movieMock.copy(id = 2),
                movieMock.copy(id = 3)
            )

            whenever(localDataSource.getFavoriteMovies()).thenReturn(emptyList())
            whenever(remoteDataSource.getMoviesFromPersonId(apiKey, 123)).thenReturn(
                Either.right(
                    movieMockList
                )
            )

            val result = moviesRepository.getMoviesFromPersonId(123)

            assert(result.isRight())
            result as Either.Right
            assertEquals(movieMockList, result.b)
        }
    }
}