package com.afrasilv.movietrack.model.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieFavDb::class], version = 1)
abstract class MovieFavDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
