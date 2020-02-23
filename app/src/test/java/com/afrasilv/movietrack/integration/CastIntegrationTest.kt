package com.afrasilv.movietrack.integration

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.afrasilv.movietrack.integration.di.DaggerMoviesComponentTest
import com.afrasilv.movietrack.integration.di.FakeCastDetailsFragmentComponent
import com.afrasilv.movietrack.integration.di.FakeCastDetailsFragmentModule
import com.afrasilv.movietrack.movieList
import com.afrasilv.movietrack.observeOnce
import com.afrasilv.movietrack.personMock
import com.afrasilv.movietrack.ui.base.convertToMovieInfo
import com.afrasilv.movietrack.ui.castdetails.CastViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CastIntegrationTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var component: FakeCastDetailsFragmentComponent
    private lateinit var viewModel: CastViewModel

    @Before
    fun setup() {
        component = DaggerMoviesComponentTest.factory().create().plus(FakeCastDetailsFragmentModule())
        viewModel = component.castViewModel
    }

    @Test
    fun `check getMoviesByCastId`() {
        viewModel.getMoviesByCastId("name")

        viewModel.model.observeForever { event ->
            val content = event.peekContent()
            if (content is CastViewModel.UiModel.PersonData) {
                Assert.assertEquals(content.person, personMock)
            }
        }
    }

    @Test
    fun `check getMoviesByCastId check moviedata`() {
        viewModel.getMoviesByCastId("name")

        viewModel.model.observeForever { event ->
            val content = event.peekContent()
            if (content is CastViewModel.UiModel.ShowMovies) {
                Assert.assertEquals(content.movieList, movieList.map { it.convertToMovieInfo() })
            }
        }
    }
}