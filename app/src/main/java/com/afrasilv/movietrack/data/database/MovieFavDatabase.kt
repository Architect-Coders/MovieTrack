package com.afrasilv.movietrack.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieFavDb::class], version = 1)
abstract class MovieFavDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
