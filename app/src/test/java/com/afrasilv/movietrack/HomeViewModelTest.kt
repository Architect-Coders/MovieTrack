package com.afrasilv.movietrack

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import arrow.core.Either
import com.afrasilv.domain.ErrorType
import com.afrasilv.domain.Failure
import com.afrasilv.domain.FailureModel
import com.afrasilv.movietrack.ui.base.convertToMovieInfo
import com.afrasilv.movietrack.ui.home.HomeViewModel
import com.afrasilv.usecases.GetPopularMovies
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getPopularMovies: GetPopularMovies

    @Mock
    lateinit var observer: Observer<HomeViewModel.UiModel>

    private lateinit var vm: HomeViewModel

    @Before
    fun setUp() {
        vm = HomeViewModel(getPopularMovies, Dispatchers.Unconfined)
    }

    @Test
    fun `when discoverMovies showLoading`() {
        runBlocking {

            val failureModel = FailureModel("", "", "", ErrorType.SERVICE_ERROR)
            whenever(getPopularMovies.invoke()).thenReturn(
                Either.left(
                    Failure(
                        failureModel
                    )
                )
            )
            vm.model.observeForever(observer)

            vm.discoverMovies()

            verify(observer).onChanged(HomeViewModel.UiModel.Loading)
        }
    }

    @Test
    fun `when discoverMovies show content`() {
        runBlocking {

            val movieMockList = listOf(
                movieMock.copy(id = 0),
                movieMock.copy(id = 1),
                movieMock.copy(id = 2),
                movieMock.copy(id = 3)
            )

            val movieInfoListMock = movieMockList.map { it.convertToMovieInfo() }

            whenever(getPopularMovies.invoke()).thenReturn(
                Either.right(movieMockList)
            )
            vm.model.observeForever(observer)

            vm.discoverMovies()

            verify(observer).onChanged(HomeViewModel.UiModel.Content(movieInfoListMock))
        }
    }
}