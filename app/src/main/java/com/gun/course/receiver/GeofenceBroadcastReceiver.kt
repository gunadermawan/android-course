package com.gun.course.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent

class GeofenceBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        if (geofencingEvent != null) {
            if (geofencingEvent.hasError()) {
                val errorMessage =
                    GeofenceStatusCodes.getStatusCodeString(geofencingEvent.errorCode)
                Log.e("GeoFenceError", "onReceive: $errorMessage")
                return
            }
        }
        val geofenceTransition = geofencingEvent?.geofenceTransition
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER || geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
            val geofence = geofencingEvent.triggeringGeofences?.get(0)
            val requestId = geofence?.requestId
            val transistionType = if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
                Log.d("GEOFENCE", "onReceive: Enter")
            } else {
                Log.d("GEOFENCE", "onReceive: Exit")
            }
            Toast.makeText(context, "GEOFENCE $requestId $transistionType", Toast.LENGTH_SHORT)
                .show()
        }
    }
}