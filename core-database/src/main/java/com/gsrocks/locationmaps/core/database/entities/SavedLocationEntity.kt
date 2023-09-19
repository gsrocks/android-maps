package com.gsrocks.locationmaps.core.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_location")
data class SavedLocationEntity(
    val latitude: Double,
    val longitude: Double,
    val title: String,
    val description: String? = null,
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
)
