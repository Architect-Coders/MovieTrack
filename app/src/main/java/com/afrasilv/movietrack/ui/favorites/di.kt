package com.afrasilv.movietrack.ui.favorites

import com.afrasilv.data.repository.MoviesRepository
import com.afrasilv.usecases.GetFavoriteMovies
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class FavoritesFragmentModule {

    @Provides
    fun favoritesViewModelProvider(getFavoriteMovies: GetFavoriteMovies) = FavoritesViewModel(getFavoriteMovies)

    @Provides
    fun getFavoriteMoviesProvider(moviesRepository: MoviesRepository) =
        GetFavoriteMovies(moviesRepository)
}

@Subcomponent( modules = [(FavoritesFragmentModule::class)])
interface FavoritesFragmentComponent {
    val favoritesViewModel: FavoritesViewModel
}