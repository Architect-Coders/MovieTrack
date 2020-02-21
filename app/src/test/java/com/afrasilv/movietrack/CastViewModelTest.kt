package com.afrasilv.movietrack

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import arrow.core.Either
import com.afrasilv.domain.Person
import com.afrasilv.movietrack.ui.base.convertToMovieInfo
import com.afrasilv.movietrack.ui.castdetails.CastViewModel
import com.afrasilv.usecases.GetMoviesFromPersonId
import com.afrasilv.usecases.GetPersonDataById
import com.afrasilv.usecases.SearchPerson
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CastViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var searchPerson: SearchPerson

    @Mock
    lateinit var getPersonDataById: GetPersonDataById

    @Mock
    lateinit var getMoviesFromPersonId: GetMoviesFromPersonId
    
    private lateinit var vm: CastViewModel

    @Before
    fun setUp() {
        vm = CastViewModel(
            searchPerson,
            getPersonDataById,
            getMoviesFromPersonId,
            Dispatchers.Unconfined
        )
    }

    @Test
    fun `when getMoviesByCastId personData`() {
        runBlocking {

            val personMock  = mock<Person>()

            whenever(searchPerson.invoke("name")).thenReturn(
                Either.right(
                    123
                )
            )

            whenever(getPersonDataById.invoke(123)).thenReturn(
                Either.right(
                    personMock
                )
            )

            vm.getMoviesByCastId("name")

            vm.model.observeForever {
                val eventValue = it.peekContent()
                eventValue as CastViewModel.UiModel.PersonData
                assertEquals(personMock, eventValue.person)
            }
        }
    }

    @Test
    fun `when getMoviesByCastId other films`() {
        runBlocking {

            val movieMockList = listOf(
                movieMock.copy(id = 0),
                movieMock.copy(id = 1),
                movieMock.copy(id = 2),
                movieMock.copy(id = 3)
            )

            val movieInfoListMock = movieMockList.map { it.convertToMovieInfo() }


            whenever(searchPerson.invoke("name")).thenReturn(
                Either.right(
                    123
                )
            )

            whenever(getMoviesFromPersonId.invoke(123)).thenReturn(
                Either.right(
                    movieMockList
                )
            )

            vm.getMoviesByCastId("name")

            vm.model.observeForever {
                val eventValue = it.peekContent()
                eventValue as CastViewModel.UiModel.ShowMovies
                assertEquals(movieInfoListMock, eventValue.movieList)
            }
        }
    }
}