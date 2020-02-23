package com.afrasilv.movietrack.integration.di

import com.afrasilv.movietrack.di.DataModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [FakeAppModule::class, DataModule::class])
interface MoviesComponentTest {

    fun plus(module: FakeHomeFragmentModule): FakeHomeFragmentComponent
    fun plus(module: FakeFavoritesFragmentModule): FakeFavoritesFragmentComponent
    fun plus(searchFragmentModule: FakeSearchFragmentModule): FakeSearchFragmentComponent
    fun plus(castDetailsFragmentModule: FakeCastDetailsFragmentModule): FakeCastDetailsFragmentComponent
    fun plus(detailsFragmentModule: FakeDetailsFragmentModule): FakeDetailsFragmentComponent

    @Component.Factory
    interface Factory {
        fun create(): MoviesComponentTest
    }
}