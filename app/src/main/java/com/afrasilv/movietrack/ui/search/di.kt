package com.afrasilv.movietrack.ui.search

import com.afrasilv.data.repository.MoviesRepository
import com.afrasilv.usecases.SearchMoviesByName
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kotlinx.coroutines.Dispatchers

@Module
class SearchFragmentModule {

    @Provides
    fun favoritesViewModelProvider(searchMoviesByName: SearchMoviesByName) = SearchViewModel(searchMoviesByName, Dispatchers.Main)

    @Provides
    fun searchMoviesByName(moviesRepository: MoviesRepository) =
        SearchMoviesByName(moviesRepository)
}

@Subcomponent( modules = [(SearchFragmentModule::class)])
interface SearchFragmentComponent {
    val searchViewModel: SearchViewModel
}