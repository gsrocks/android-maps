package com.gsrocks.locationmaps.core.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserLocation::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userLocationDao(): UserLocationDao
}
