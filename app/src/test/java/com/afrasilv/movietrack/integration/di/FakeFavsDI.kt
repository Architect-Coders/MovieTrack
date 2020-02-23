package com.afrasilv.movietrack.integration.di

import com.afrasilv.data.repository.MoviesRepository
import com.afrasilv.movietrack.ui.favorites.FavoritesViewModel
import com.afrasilv.usecases.GetFavoriteMovies
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kotlinx.coroutines.Dispatchers

@Module
class FakeFavoritesFragmentModule {

    @Provides
    fun favoritesViewModelProvider(getFavoriteMovies: GetFavoriteMovies) = FavoritesViewModel(getFavoriteMovies, Dispatchers.Unconfined)

    @Provides
    fun getFavoriteMoviesProvider(moviesRepository: MoviesRepository) =
        GetFavoriteMovies(moviesRepository)
}

@Subcomponent( modules = [(FakeFavoritesFragmentModule::class)])
interface FakeFavoritesFragmentComponent {
    val favoritesViewModel: FavoritesViewModel
}