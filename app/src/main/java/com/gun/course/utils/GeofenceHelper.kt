package com.gun.course.utils

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices

class GeofenceHelper(private val context: Context) {
    private val geofenceClient = LocationServices.getGeofencingClient(context)
    fun createGeofence(
        latitude: Double,
        longitude: Double,
        radius: Float,
        geofenceId: String
    ): Geofence {
        return Geofence.Builder()
            .setRequestId(geofenceId)
            .setCircularRegion(latitude, longitude, radius)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
            .build()
    }

    fun createGeofenceRequest(geofence: Geofence): GeofencingRequest {
        return GeofencingRequest.Builder()
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .addGeofence(geofence)
            .build()
    }

    fun addGeofence(geofence: Geofence, pendingIntent: PendingIntent) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            try {
                geofenceClient.addGeofences(createGeofenceRequest(geofence), pendingIntent)
                    .addOnSuccessListener { }
                    .addOnFailureListener {}
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}