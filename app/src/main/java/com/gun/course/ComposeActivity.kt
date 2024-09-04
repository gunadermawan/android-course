package com.gun.course

import android.Manifest
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
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
import com.gun.course.service.MyBoundService
import com.gun.course.service.MyForegroundService
import com.gun.course.ui.theme.CourseAppTheme

class ComposeActivity : ComponentActivity() {

    private val REQUEST_NOTIFICATION_PERMISSION = 1
    private var myBoundService: MyBoundService? = null
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MyBoundService.LocalBinder
            myBoundService = binder.getService()
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            myBoundService = null
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestNotificationPermission()
        setContent {
            CourseAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyBoundServiceScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }


    @Composable
    fun MyBoundServiceScreen(modifier: Modifier = Modifier) {
        val context = LocalContext.current
        var isServiceBound by remember {
            mutableStateOf(false)
        }

        Column(modifier = modifier.fillMaxSize()) {
            Button(onClick = {
                if (isServiceBound) {
                    context.unbindService(connection)
                    isServiceBound = false
                } else {
                    val intent = Intent(context, MyBoundService::class.java)
                    context.bindService(intent, connection, BIND_AUTO_CREATE)
                    isServiceBound = true
                }

            }) {
                Text(text = if (isServiceBound) "Unbind service" else "Bind service")
            }

            Spacer(modifier = modifier.height(16.dp))

            Button(onClick = {
                myBoundService?.performOperation()
            }, enabled = isServiceBound) {
                Text(text = "Perform operation")
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


}