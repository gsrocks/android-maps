package com.gsrocks.locationmaps.di

import android.content.Context
import android.content.pm.PackageManager
import com.google.maps.GeoApiContext
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideGeoApiContext(@ApplicationContext context: Context): GeoApiContext {
        val applicationInfo = context.packageManager.getApplicationInfo(
            context.packageName,
            PackageManager.GET_META_DATA
        )
        val apiKey = applicationInfo.metaData.getString("com.google.android.geo.API_KEY")
        return GeoApiContext.Builder()
            .apiKey(apiKey)
            .build()
    }
}
