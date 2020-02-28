package com.afrasilv.movietrack

import android.app.Application
import com.afrasilv.movietrack.di.MoviesComponent

class UiTestMovieTrackApp : MovieTrackApp() {

    lateinit var uiTestComponent: UiTestComponent

    fun createComponent(app: Application) {
        uiTestComponent = initMoviesComponent(app) as UiTestComponent
    }

    override fun initMoviesComponent(app: Application): MoviesComponent {
        return DaggerUiTestComponent.factory().create(app)
    }
}