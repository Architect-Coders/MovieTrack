package com.afrasilv.movietrack.integration

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.afrasilv.movietrack.integration.di.DaggerMoviesComponentTest
import com.afrasilv.movietrack.integration.di.FakeHomeFragmentComponent
import com.afrasilv.movietrack.integration.di.FakeHomeFragmentModule
import com.afrasilv.movietrack.movieList
import com.afrasilv.movietrack.ui.base.convertToMovieInfo
import com.afrasilv.movietrack.ui.home.HomeViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class HomeIntegrationTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var component: FakeHomeFragmentComponent
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        component = DaggerMoviesComponentTest.factory().create().plus(FakeHomeFragmentModule())
        viewModel = component.homeViewModel
    }

    @Test
    fun `call to discoverMovies`() {
        viewModel.discoverMovies()

        viewModel.model.observeForever { event ->
            assert(event is HomeViewModel.UiModel.Content)
            event as HomeViewModel.UiModel.Content
            assertEquals(event.movies, movieList.map { it.convertToMovieInfo() })
        }
    }
}