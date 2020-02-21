package com.afrasilv.movietrack

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import arrow.core.Either
import com.afrasilv.movietrack.ui.base.convertToMovieInfo
import com.afrasilv.movietrack.ui.favorites.FavoritesViewModel
import com.afrasilv.usecases.GetFavoriteMovies
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
class FavoritesViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getFavoriteMovies: GetFavoriteMovies

    @Mock
    lateinit var observer: Observer<FavoritesViewModel.UiModel>

    private lateinit var vm: FavoritesViewModel

    @Before
    fun setUp() {
        vm = FavoritesViewModel(getFavoriteMovies, Dispatchers.Unconfined)
    }

    @Test
    fun `when getFavoriteMovies show content`() {
        runBlocking {

            val movieMockList = listOf(
                movieMock.copy(id = 0),
                movieMock.copy(id = 1),
                movieMock.copy(id = 2),
                movieMock.copy(id = 3)
            )

            val movieInfoListMock = movieMockList.map { it.convertToMovieInfo() }

            whenever(getFavoriteMovies.invoke()).thenReturn(
                Either.right(movieMockList)
            )
            vm.model.observeForever(observer)

            vm.getFavoriteMovies()

            verify(observer).onChanged(FavoritesViewModel.UiModel.Content(movieInfoListMock))
        }
    }
}