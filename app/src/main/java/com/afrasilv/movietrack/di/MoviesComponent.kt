package com.afrasilv.movietrack.di

import android.app.Application
import com.afrasilv.movietrack.ui.castdetails.CastViewModel
import com.afrasilv.movietrack.ui.details.DetailsViewModel
import com.afrasilv.movietrack.ui.favorites.FavoritesViewModel
import com.afrasilv.movietrack.ui.home.HomeViewModel
import com.afrasilv.movietrack.ui.search.SearchViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class, UseCaseModule::class, ViewModelsModule::class])
interface MoviesComponent {

    val homeViewModel: HomeViewModel
    val searchViewModel: SearchViewModel
    val favoritesViewModel: FavoritesViewModel
    val detailsViewModel: DetailsViewModel
    val castViewModel: CastViewModel

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): MoviesComponent
    }
}