package com.afrasilv.movietrack.integration

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.afrasilv.movietrack.integration.di.DaggerMoviesComponentTest
import com.afrasilv.movietrack.integration.di.FakeFavoritesFragmentComponent
import com.afrasilv.movietrack.integration.di.FakeFavoritesFragmentModule
import com.afrasilv.movietrack.movieList
import com.afrasilv.movietrack.ui.base.convertToMovieInfo
import com.afrasilv.movietrack.ui.favorites.FavoritesViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoritesIntegrationTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var component: FakeFavoritesFragmentComponent
    private lateinit var viewModel: FavoritesViewModel

    @Before
    fun setup() {
        component = DaggerMoviesComponentTest.factory().create().plus(FakeFavoritesFragmentModule())
        viewModel = component.favoritesViewModel
    }

    @Test
    fun `obtain favorites movies`() {
        viewModel.getFavoriteMovies()

        viewModel.model.observeForever { event ->
            assert(event is FavoritesViewModel.UiModel.Content)
            event as FavoritesViewModel.UiModel.Content
            Assert.assertEquals(event.movies, movieList.map { it.convertToMovieInfo() })
        }

    }
}