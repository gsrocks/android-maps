package com.gsrocks.locationmaps.core.database.di

import android.content.Context
import androidx.room.Room
import com.gsrocks.locationmaps.core.database.AppDatabase
import com.gsrocks.locationmaps.core.database.daos.SavedLocationDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideSavedLocationDao(appDatabase: AppDatabase): SavedLocationDao {
        return appDatabase.savedLocationDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "locations_database"
        ).build()
    }
}
