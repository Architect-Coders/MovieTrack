package com.afrasilv.movietrack

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import arrow.core.Either
import com.afrasilv.movietrack.ui.base.convertToCast
import com.afrasilv.movietrack.ui.details.DetailsViewModel
import com.afrasilv.usecases.CheckIfMovieIsFav
import com.afrasilv.usecases.GetMovieCredits
import com.afrasilv.usecases.RemoveIfFav
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
class DetailsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var checkIfMovieIsFav: CheckIfMovieIsFav

    @Mock
    lateinit var removeIfFav: RemoveIfFav

    @Mock
    lateinit var getMovieCredits: GetMovieCredits

    private lateinit var vm: DetailsViewModel

    @Before
    fun setUp() {
        vm = DetailsViewModel(checkIfMovieIsFav, removeIfFav, getMovieCredits, Dispatchers.Unconfined)
    }

    @Test
    fun `when checkIsFav and return false`() {
        runBlocking {

            whenever(checkIfMovieIsFav.invoke(123)).thenReturn(
                false
            )

            vm.checkIsFav(123)

            vm.model.observeOnce {
                val eventValue = it.peekContent()
                eventValue as DetailsViewModel.UiModel.IsFav
                assert(!eventValue.isFav)
            }
        }
    }

    @Test
    fun `when checkIsFav and return true`() {
        runBlocking {

            whenever(checkIfMovieIsFav.invoke(123)).thenReturn(
                true
            )

            vm.checkIsFav(123)

            vm.model.observeOnce {
                val eventValue = it.peekContent()
                eventValue as DetailsViewModel.UiModel.IsFav
                assert(eventValue.isFav)
            }
        }
    }

    @Test
    fun `when discoverMovies showLoading`() {
        runBlocking {

            val castRemoteMockList = listOf(
                castRemoteMock.copy(id = 0),
                castRemoteMock.copy(id = 1),
                castRemoteMock.copy(id = 2),
                castRemoteMock.copy(id = 3)
            )

            val castMockList = castRemoteMockList.map { it.convertToCast() }

            whenever(getMovieCredits.invoke(123)).thenReturn(
                Either.right(castRemoteMockList)
            )

            vm.getCredits(123)

            vm.model.observeOnce {
                val eventValue = it.peekContent()
                eventValue as DetailsViewModel.UiModel.ShowCast
                assertEquals(castMockList, eventValue.castList)
            }
        }
    }
}