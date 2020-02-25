package com.afrasilv.movietrack.di

import android.app.Application
import com.afrasilv.movietrack.ui.castdetails.CastDetailsFragmentComponent
import com.afrasilv.movietrack.ui.castdetails.CastDetailsFragmentModule
import com.afrasilv.movietrack.ui.details.DetailsFragmentComponent
import com.afrasilv.movietrack.ui.details.DetailsFragmentModule
import com.afrasilv.movietrack.ui.favorites.FavoritesFragmentComponent
import com.afrasilv.movietrack.ui.favorites.FavoritesFragmentModule
import com.afrasilv.movietrack.ui.home.HomeFragmentComponent
import com.afrasilv.movietrack.ui.home.HomeFragmentModule
import com.afrasilv.movietrack.ui.search.SearchFragmentComponent
import com.afrasilv.movietrack.ui.search.SearchFragmentModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ServiceModule::class, DataModule::class])
interface MoviesComponent {

    fun plus(module: HomeFragmentModule): HomeFragmentComponent
    fun plus(module: FavoritesFragmentModule): FavoritesFragmentComponent
    fun plus(searchFragmentModule: SearchFragmentModule): SearchFragmentComponent
    fun plus(castDetailsFragmentModule: CastDetailsFragmentModule): CastDetailsFragmentComponent
    fun plus(detailsFragmentModule: DetailsFragmentModule): DetailsFragmentComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): MoviesComponent
    }
}