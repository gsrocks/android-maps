package com.gsrocks.locationmaps.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gsrocks.locationmaps.core.database.daos.SavedLocationDao
import com.gsrocks.locationmaps.core.database.entities.SavedLocationEntity

@Database(entities = [SavedLocationEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun savedLocationDao(): SavedLocationDao
}
