package com.afrasilv.movietrack.di

import com.afrasilv.data.repository.*
import com.afrasilv.data.source.LocalDataSource
import com.afrasilv.data.source.LocationDataSource
import com.afrasilv.data.source.RemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class DataModule {

    @Provides
    fun locationRepositoryProvider(
        locationDataSource: LocationDataSource,
        permissionChecker: PermissionChecker
    ) = LocationRepository(locationDataSource, permissionChecker)

    @Provides
    fun moviesRepositoryProvider(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource,
        locationRepository: LocationRepository,
        @Named("apiKey") apiKey: String
    ) = MoviesRepository(localDataSource, remoteDataSource, locationRepository, apiKey)

    @Provides
    fun castRepositoryProvider(
        remoteDataSource: RemoteDataSource,
        @Named("apiKey") apiKey: String
    ) = CastRepository(remoteDataSource, apiKey)

    @Provides
    fun creditsRepositoryProvider(
        remoteDataSource: RemoteDataSource,
        @Named("apiKey") apiKey: String
    ) = CreditsRepository(remoteDataSource, apiKey)
}