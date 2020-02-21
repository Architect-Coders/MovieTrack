package com.afrasilv.movietrack.ui.home

import com.afrasilv.data.repository.MoviesRepository
import com.afrasilv.usecases.GetPopularMovies
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kotlinx.coroutines.Dispatchers

@Module
class HomeFragmentModule {

    @Provides
    fun homeViewModelProvider(getPopularMovies: GetPopularMovies) = HomeViewModel(getPopularMovies, Dispatchers.Main)

    @Provides
    fun getPopularMoviesProvider(moviesRepository: MoviesRepository) =
        GetPopularMovies(moviesRepository)
}

@Subcomponent( modules = [(HomeFragmentModule::class)])
interface HomeFragmentComponent {
    val homeViewModel: HomeViewModel
}