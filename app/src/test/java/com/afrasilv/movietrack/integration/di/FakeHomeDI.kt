package com.afrasilv.movietrack.integration.di

import com.afrasilv.data.repository.MoviesRepository
import com.afrasilv.movietrack.ui.home.HomeViewModel
import com.afrasilv.usecases.GetPopularMovies
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kotlinx.coroutines.Dispatchers

@Module
class FakeHomeFragmentModule {

    @Provides
    fun homeViewModelProvider(getPopularMovies: GetPopularMovies) = HomeViewModel(getPopularMovies, Dispatchers.Unconfined)

    @Provides
    fun getPopularMoviesProvider(moviesRepository: MoviesRepository) =
        GetPopularMovies(moviesRepository)
}

@Subcomponent( modules = [(FakeHomeFragmentModule::class)])
interface FakeHomeFragmentComponent {
    val homeViewModel: HomeViewModel
}