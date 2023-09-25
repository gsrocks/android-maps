package com.gsrocks.locationmaps.core.geo.geofencing

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.gsrocks.locationmaps.core.model.Coordinates
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DefaultGeofencingDataSource @Inject constructor(
    private val geofencingClient: GeofencingClient,
    @ApplicationContext private val context: Context
) : GeofencingDataSource {
    override fun createGeofence(
        requestId: String,
        coordinates: Coordinates,
        radius: Float,
        durationMills: Long
    ) {
        val geofence = Geofence.Builder()
            .setRequestId(requestId)
            .setCircularRegion(coordinates.latitude, coordinates.longitude, radius)
            .setExpirationDuration(durationMills)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
            .build()

        val request = GeofencingRequest.Builder()
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .addGeofence(geofence)
            .build()

        val broadcastIntent = Intent(context, GeofenceBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            broadcastIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )

        if (
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            geofencingClient.addGeofences(request, pendingIntent).run {
                addOnSuccessListener {

                }
                addOnFailureListener {

                }
            }
        }
    }
}
