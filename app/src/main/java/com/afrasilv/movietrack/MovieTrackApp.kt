package com.afrasilv.movietrack

import android.app.Application
import androidx.room.Room
import com.afrasilv.movietrack.data.database.MovieFavDatabase

class MovieTrackApp : Application() {

    lateinit var db: MovieFavDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            this,
            MovieFavDatabase::class.java, "movie-track-db"
        ).build()
    }
}