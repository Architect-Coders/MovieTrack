package com.afrasilv.movietrack.integration.di

import com.afrasilv.movietrack.di.DataModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [FakeAppModule::class, DataModule::class])
interface MoviesComponentTest {

    fun plus(module: FakeHomeFragmentModule): FakeHomeFragmentComponent
//    fun plus(module: FavoritesFragmentModule): FavoritesFragmentComponent
//    fun plus(searchFragmentModule: SearchFragmentModule): SearchFragmentComponent
//    fun plus(castDetailsFragmentModule: CastDetailsFragmentModule): CastDetailsFragmentComponent
//    fun plus(detailsFragmentModule: DetailsFragmentModule): DetailsFragmentComponent

    @Component.Factory
    interface Factory {
        fun create(): MoviesComponentTest
    }
}