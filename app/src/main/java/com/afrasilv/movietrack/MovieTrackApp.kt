package com.afrasilv.movietrack

import android.app.Application
import com.afrasilv.movietrack.di.DaggerMoviesComponent
import com.afrasilv.movietrack.di.MoviesComponent

class MovieTrackApp : Application() {

    lateinit var component: MoviesComponent
        private set

    override fun onCreate() {
        super.onCreate()

        component = DaggerMoviesComponent
            .factory()
            .create(this)
    }
}