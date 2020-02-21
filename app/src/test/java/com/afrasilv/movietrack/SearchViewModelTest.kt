package com.afrasilv.movietrack

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import arrow.core.Either
import com.afrasilv.movietrack.ui.base.convertToMovieInfo
import com.afrasilv.movietrack.ui.search.SearchViewModel
import com.afrasilv.usecases.SearchMoviesByName
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
class SearchViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var searchMoviesByName: SearchMoviesByName

    @Mock
    lateinit var observer: Observer<SearchViewModel.UiModel>

    private lateinit var vm: SearchViewModel

    @Before
    fun setUp() {
        vm = SearchViewModel(searchMoviesByName, Dispatchers.Unconfined)
    }

    @Test
    fun `when searchMovies show content`() {
        runBlocking {

            val movieMockList = listOf(
                movieMock.copy(id = 0),
                movieMock.copy(id = 1),
                movieMock.copy(id = 2),
                movieMock.copy(id = 3)
            )

            val movieInfoListMock = movieMockList.map { it.convertToMovieInfo() }

            whenever(searchMoviesByName.invoke("name")).thenReturn(
                Either.right(movieMockList)
            )
            vm.model.observeForever(observer)

            vm.searchMovies("name")

            verify(observer).onChanged(SearchViewModel.UiModel.Content(movieInfoListMock))
        }
    }
}