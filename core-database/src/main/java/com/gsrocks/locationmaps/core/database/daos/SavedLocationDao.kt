package com.gsrocks.locationmaps.core.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gsrocks.locationmaps.core.database.entities.SavedLocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedLocationDao {
    @Query("SELECT * FROM saved_location ORDER BY uid DESC LIMIT 10")
    fun getSavedLocationsFlow(): Flow<List<SavedLocationEntity>>

    @Insert
    suspend fun insertSavedLocation(item: SavedLocationEntity)
}
