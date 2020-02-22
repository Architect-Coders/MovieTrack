package com.afrasilv.movietrack.integration.di

import com.afrasilv.data.repository.PermissionChecker
import com.afrasilv.data.source.LocalDataSource
import com.afrasilv.data.source.LocationDataSource
import com.afrasilv.data.source.RemoteDataSource
import com.afrasilv.movietrack.SERVICE_API_KEY
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class FakeAppModule {

    @Provides
    @Singleton
    @Named("apiKey")
    fun apiKeyProvider(): String = SERVICE_API_KEY

    @Singleton
    @Provides
    fun localDataSourceProvider(): LocalDataSource =
        FakeLocalDataSource()

    @Singleton
    @Provides
    fun remoteDataSourceProvider(): RemoteDataSource =
        FakeRemoteDataSource()

    @Singleton
    @Provides
    fun locationDataSourceProvider(): LocationDataSource =
        FakeLocationDataSource()

    @Singleton
    @Provides
    fun permissionCheckerProvider(): PermissionChecker =
        FakePermissionChecker()
}

