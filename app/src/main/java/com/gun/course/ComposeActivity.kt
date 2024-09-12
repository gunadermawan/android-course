package com.gun.course

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.gun.course.ui.theme.CourseAppTheme
import kotlinx.coroutines.launch

class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            CourseAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LocationTracker(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    @Composable
    fun MapsScreen(modifier: Modifier = Modifier) {
        val monas = LatLng(-6.1835998, 106.8350423)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(monas, 15f)
        }

        val uiSettings by remember {
            mutableStateOf(MapUiSettings(zoomControlsEnabled = true))
        }
        val propertis by remember {
            mutableStateOf(MapProperties(mapType = MapType.SATELLITE))
        }
        GoogleMap(
            modifier = modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = propertis,
            uiSettings = uiSettings
        ) {
            Marker(state = rememberMarkerState(position = monas), title = "Monas Marker")
        }
    }

    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun LocationTracker(modifier: Modifier = Modifier) {
        val context = LocalContext.current
        val permissionState = rememberMultiplePermissionsState(
            permissions = listOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
        var currentLocation by remember { mutableStateOf<Location?>(null) }
        val coroutineScope = rememberCoroutineScope()
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(
                LatLng(currentLocation?.latitude ?: -6.2, currentLocation?.longitude ?: 106.8), 10f
            )
        }

        fun updateLocation() {
            coroutineScope.launch {
                if (permissionState.allPermissionsGranted) {
                    try {
                        val fusedLocation = LocationServices.getFusedLocationProviderClient(context)
                        if (ActivityCompat.checkSelfPermission(
                                context,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                context,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            return@launch
                        }
                        currentLocation = fusedLocation.lastLocation.result
                        cameraPositionState.animate(
                            CameraUpdateFactory.newLatLng(
                                LatLng(
                                    currentLocation?.latitude ?: -6.2,
                                    currentLocation?.longitude ?: 106.8
                                )
                            )
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
        if (!permissionState.allPermissionsGranted) {
            Button(onClick = { permissionState.launchMultiplePermissionRequest() }) {
                Text(text = "Get Location")
            }
        } else {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GoogleMap(
                    modifier = modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState
                ) {
                    Marker(
                        state = rememberMarkerState(position = cameraPositionState.position.target),
                        title = "Current Location"
                    )
                }
            }
        }
    }
}