package com.afrasilv.movietrack.integration

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.afrasilv.movietrack.integration.di.DaggerMoviesComponentTest
import com.afrasilv.movietrack.integration.di.FakeSearchFragmentComponent
import com.afrasilv.movietrack.integration.di.FakeSearchFragmentModule
import com.afrasilv.movietrack.movieList
import com.afrasilv.movietrack.ui.base.convertToMovieInfo
import com.afrasilv.movietrack.ui.search.SearchViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchIntegrationTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var component: FakeSearchFragmentComponent
    private lateinit var viewModel: SearchViewModel

    @Before
    fun setup() {
        component = DaggerMoviesComponentTest.factory().create().plus(FakeSearchFragmentModule())
        viewModel = component.homeViewModel
    }

    @Test
    fun `call to search`() {
        viewModel.searchMovies("test")

        viewModel.model.observeForever { event ->
            assert(event is SearchViewModel.UiModel.Content)
            event as SearchViewModel.UiModel.Content
            Assert.assertEquals(event.movies, movieList.map { it.convertToMovieInfo() })
        }

    }
}