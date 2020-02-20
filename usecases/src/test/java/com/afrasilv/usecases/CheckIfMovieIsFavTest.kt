package com.afrasilv.usecases

import com.afrasilv.data.repository.MoviesRepository
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class CheckIfMovieIsFavTest {

    @Mock
    lateinit var moviesRepository: MoviesRepository

    lateinit var checkIfMovieIsFav: CheckIfMovieIsFav

    @Before
    fun setUp() {
        checkIfMovieIsFav = CheckIfMovieIsFav(moviesRepository)
    }

    @Test
    fun `invoke calls movies repository`() {
        runBlocking {

            val movie = movieMock.copy(id = 1)

            whenever(moviesRepository.checkIfMovieIsFav(movie.id)).thenReturn(true)

            val result = checkIfMovieIsFav.invoke(movie.id)

            assert(result)
        }
    }
}