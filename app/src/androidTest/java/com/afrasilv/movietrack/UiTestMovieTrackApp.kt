package com.afrasilv.movietrack

import android.app.Application

class UiTestMovieTrackApp : MovieTrackApp() {

    lateinit var uiTestComponent: UiTestComponent

    fun createComponent(app: Application) {
        uiTestComponent = DaggerUiTestComponent.factory().create(app)
    }
}