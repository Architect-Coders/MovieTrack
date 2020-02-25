package com.afrasilv.movietrack.di

import com.afrasilv.movietrack.data.retrofit.RetrofitAPI
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class ServiceModule {

    @Provides
    @Singleton
    @Named("baseUrl")
    fun baseUrlProvider(): String = "https://api.themoviedb.org/3/"

    @Provides
    @Singleton
    fun retrofitAPIProvider(
        @Named("baseUrl") baseUrl: String) = RetrofitAPI(baseUrl)

}