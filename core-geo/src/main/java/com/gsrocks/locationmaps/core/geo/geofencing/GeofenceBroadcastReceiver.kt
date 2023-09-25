package com.gsrocks.locationmaps.core.geo.geofencing

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent
import kotlin.random.Random

private const val TAG = "GeofenceBroadcast"

class GeofenceBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent) ?: return
        if (geofencingEvent.hasError()) {
            val errorMessage = GeofenceStatusCodes.getStatusCodeString(geofencingEvent.errorCode)
            Log.e(TAG, errorMessage)
        }

        when (val transition = geofencingEvent.geofenceTransition) {
            Geofence.GEOFENCE_TRANSITION_ENTER,
            Geofence.GEOFENCE_TRANSITION_EXIT -> {
                val triggeringGeofences = geofencingEvent.triggeringGeofences.orEmpty()
                val transitionDetails = getGeofenceTransitionDetails(
                    transition,
                    triggeringGeofences
                )
                if (transitionDetails != null) {
                    sendNotification(context, transitionDetails)
                    Log.d(TAG, transitionDetails)
                }
            }

            else -> Log.e(TAG, "Invalid geofence transition type: $transition")
        }
    }

    private fun getGeofenceTransitionDetails(
        geofenceTransition: Int,
        triggeringGeofences: List<Geofence>
    ): String? {
        return when (geofenceTransition) {
            Geofence.GEOFENCE_TRANSITION_ENTER ->
                "You entered zones: ${triggeringGeofences.joinToString()}"

            Geofence.GEOFENCE_TRANSITION_EXIT ->
                "You exited zones: ${triggeringGeofences.joinToString()}"

            else -> null
        }
    }

    private fun sendNotification(context: Context, transitionDetails: String) {
        val notificationManager = NotificationManagerCompat.from(context)

        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            "Geofencing",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Geofencing notifications"
            enableVibration(true)
        }

        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(transitionDetails)
            .setSmallIcon(androidx.core.R.drawable.ic_call_answer)
            .build()

        val id = Random.nextInt()

        if (
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            notificationManager.notify(id, notification)
        }
    }

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "com.gsrocks.locationmaps.geofence"
    }
}
