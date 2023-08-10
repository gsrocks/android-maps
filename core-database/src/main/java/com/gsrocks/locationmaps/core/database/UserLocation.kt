package com.gsrocks.locationmaps.core.database

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Entity
data class UserLocation(
    val name: String
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}

@Dao
interface UserLocationDao {
    @Query("SELECT * FROM userlocation ORDER BY uid DESC LIMIT 10")
    fun getUserLocations(): Flow<List<UserLocation>>

    @Insert
    suspend fun insertUserLocation(item: UserLocation)
}
