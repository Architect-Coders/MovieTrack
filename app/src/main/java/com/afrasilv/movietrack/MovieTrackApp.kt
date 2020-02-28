package com.afrasilv.movietrack

import android.app.Application
import com.afrasilv.movietrack.di.DaggerMoviesComponent
import com.afrasilv.movietrack.di.MoviesComponent

open class MovieTrackApp : Application() {

    lateinit var component: MoviesComponent
        private set

    override fun onCreate() {
        super.onCreate()

        component = initMoviesComponent(this)
    }

    open fun initMoviesComponent(app: Application) = DaggerMoviesComponent
        .factory()
        .create(app)
}