package com.gun.course

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.gun.course.service.CountingService
import com.gun.course.service.MyForegroundService
import com.gun.course.ui.theme.CourseAppTheme

class ComposeActivity : ComponentActivity() {

    private val REQUEST_NOTIFICATION_PERMISSION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestNotificationPermission()
        setContent {
            CourseAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ForegroundServiceScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    REQUEST_NOTIFICATION_PERMISSION
                )
            }
        }
    }


    @Composable
    fun ForegroundServiceScreen(modifier: Modifier = Modifier) {
        val context = LocalContext.current
        var isServiceRunning by remember {
            mutableStateOf(false)
        }
        Column(modifier = modifier.fillMaxSize()) {
            Button(onClick = {
                val intent = Intent(context, MyForegroundService::class.java)
                if (isServiceRunning) {
                    context.stopService(intent)
                    isServiceRunning = false
                } else {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU || ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.POST_NOTIFICATIONS
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        context.startService(intent)
                        isServiceRunning = true
                    } else {
                        requestNotificationPermission()
                    }
                }
            }) {
                Text(text = if (isServiceRunning) "Stop foreground service" else "Start foreground service")
            }
        }
    }

    @Composable
    fun CountingService(modifier: Modifier = Modifier) {
        val context = LocalContext.current
        var isServiceRunning by remember {
            mutableStateOf(false)
        }

        Column(modifier = modifier.fillMaxSize()) {
            Button(onClick = {
                val intent = Intent(context, CountingService::class.java)
                if (isServiceRunning) {
                    context.stopService(intent)
                    isServiceRunning = false
                } else {
                    context.startService(intent)
                    isServiceRunning = true
                }
            }) {
                Text(text = if (isServiceRunning) "Stop Counting Service" else "Start Counting Service")
            }
        }
    }

    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun RequestPermissionScreen(modifier: Modifier = Modifier) {
        val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (cameraPermissionState.status.isGranted) {
                Text(text = "Permission Granted!, you can access the camera")
            } else {
                Text(text = "Permission is required to proceed")
                Spacer(modifier = modifier.height(16.dp))
                Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                    Text(text = "Request Permission")
                }
            }
        }
    }
}