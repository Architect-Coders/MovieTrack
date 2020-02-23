package com.afrasilv.movietrack.integration

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.afrasilv.movietrack.castRemoteList
import com.afrasilv.movietrack.integration.di.DaggerMoviesComponentTest
import com.afrasilv.movietrack.integration.di.FakeDetailsFragmentComponent
import com.afrasilv.movietrack.integration.di.FakeDetailsFragmentModule
import com.afrasilv.movietrack.movieMock
import com.afrasilv.movietrack.ui.base.convertToCast
import com.afrasilv.movietrack.ui.base.convertToMovieInfo
import com.afrasilv.movietrack.ui.details.DetailsViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailsIntegrationTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var component: FakeDetailsFragmentComponent
    private lateinit var viewModel: DetailsViewModel

    @Before
    fun setup() {
        component = DaggerMoviesComponentTest.factory().create().plus(FakeDetailsFragmentModule())
        viewModel = component.detailsViewModel
    }

    @Test
    fun `check if is fav`() {
        viewModel.checkIsFav(1)

        viewModel.model.observeForever { event ->
            val content = event.peekContent()
            content as DetailsViewModel.UiModel.IsFav
            Assert.assertEquals(true, content.isFav)
        }
    }

    @Test
    fun `check if is fav return false`() {
        viewModel.checkIsFav(123)

        viewModel.model.observeForever { event ->
            val content = event.peekContent()
            content as DetailsViewModel.UiModel.IsFav
            Assert.assertEquals(false, content.isFav)
        }
    }

    @Test
    fun `remove if is fav`() {
        viewModel.removeIfFav(movieMock.convertToMovieInfo())

        viewModel.model.observeForever { event ->
            val content = event.peekContent()
            assert(content is DetailsViewModel.UiModel.IsFav)
        }
    }

    @Test
    fun `check getCredits`() {
        viewModel.getCredits(1)

        viewModel.model.observeForever { event ->
            val content = event.peekContent()
            content as DetailsViewModel.UiModel.ShowCast
            Assert.assertEquals(content.castList, castRemoteList.map { it.convertToCast() })
        }
    }
}