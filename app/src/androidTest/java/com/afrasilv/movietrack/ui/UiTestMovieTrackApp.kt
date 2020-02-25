package com.afrasilv.movietrack.ui

import com.afrasilv.movietrack.MovieTrackApp

class UiTestMovieTrackApp : MovieTrackApp() {

    override fun initMoviesComponent() = DaggerUiTestComponent.factory().create(this)

}