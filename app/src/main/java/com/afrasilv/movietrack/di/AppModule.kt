package com.afrasilv.movietrack.di

import android.app.Application
import androidx.room.Room
import com.afrasilv.data.repository.PermissionChecker
import com.afrasilv.data.source.LocalDataSource
import com.afrasilv.data.source.LocationDataSource
import com.afrasilv.data.source.RemoteDataSource
import com.afrasilv.movietrack.SERVICE_API_KEY
import com.afrasilv.movietrack.data.AndroidPermissionChecker
import com.afrasilv.movietrack.data.database.MovieFavDatabase
import com.afrasilv.movietrack.data.database.RoomDataSource
import com.afrasilv.movietrack.data.retrofit.MovieTrackRemoteDataSource
import com.afrasilv.movietrack.data.retrofit.RetrofitAPI
import com.afrasilv.movietrack.ui.location.PlayServicesLocationDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    @Named("apiKey")
    fun apiKeyProvider(): String = SERVICE_API_KEY

    @Provides
    @Singleton
    fun databaseProvider(app: Application) = Room.databaseBuilder(
        app,
        MovieFavDatabase::class.java,
        "movie-track-db"
    ).build()

    @Provides
    fun localDataSourceProvider(db: MovieFavDatabase): LocalDataSource = RoomDataSource(db)

    @Provides
    fun remoteDataSourceProvider(retrofitAPI: RetrofitAPI): RemoteDataSource =
        MovieTrackRemoteDataSource(retrofitAPI)

    @Provides
    fun locationDataSourceProvider(app: Application): LocationDataSource =
        PlayServicesLocationDataSource(app)

    @Provides
    fun permissionCheckerProvider(app: Application): PermissionChecker =
        AndroidPermissionChecker(app)
}