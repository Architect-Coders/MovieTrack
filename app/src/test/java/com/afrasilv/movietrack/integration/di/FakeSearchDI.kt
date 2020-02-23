package com.afrasilv.movietrack.integration.di

import com.afrasilv.data.repository.MoviesRepository
import com.afrasilv.movietrack.ui.search.SearchViewModel
import com.afrasilv.usecases.SearchMoviesByName
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kotlinx.coroutines.Dispatchers

@Module
class FakeSearchFragmentModule {

    @Provides
    fun searchViewModelProvider(searchMoviesByName: SearchMoviesByName) = SearchViewModel(searchMoviesByName, Dispatchers.Unconfined)

    @Provides
    fun searchMoviesByNameProvider(moviesRepository: MoviesRepository) =
        SearchMoviesByName(moviesRepository)
}

@Subcomponent( modules = [(FakeSearchFragmentModule::class)])
interface FakeSearchFragmentComponent {
    val homeViewModel: SearchViewModel
}