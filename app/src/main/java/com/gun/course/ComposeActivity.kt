package com.gun.course

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
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

class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CourseAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MapsScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }


    @Composable
    fun ExoPlayerExample(modifier: Modifier = Modifier) {
        val context = LocalContext.current

        val exoPlayer = remember {
            ExoPlayer.Builder(context).build().apply {
                val mediaItem =
                    MediaItem.fromUri("https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4")
                setMediaItem(mediaItem)
                prepare()
            }
        }

        var isPlaying by remember { mutableStateOf(false) }

        DisposableEffect(key1 = Unit) {
            onDispose {
                exoPlayer.release()
            }
        }
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AndroidView(
                factory = { context ->
                    PlayerView(context).apply {
                        player = exoPlayer
                    }
                }, modifier = modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
            Spacer(modifier = modifier.height(16.dp))
            Button(onClick = {
                if (exoPlayer.isPlaying) {
                    exoPlayer.pause()
                } else {
                    exoPlayer.play()
                }
                isPlaying = exoPlayer.isPlaying
            }) {
                Text(text = if (isPlaying) "Pause" else "Play")
            }
        }
    }

    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun CameraAndGalleryExample(modifier: Modifier = Modifier) {
        val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
        val context = LocalContext.current
        var imageUri by remember {
            mutableStateOf<Uri?>(null)
        }

        val cameraLauncher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicturePreview()) {
                it?.let {
                    val uri = saveImageToInternalStorage(context, it)
                    imageUri = uri
                }
            }

        val galleryLauncher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    imageUri = data?.data
                }
            }

        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (cameraPermissionState.status.isGranted) {
                Button(onClick = { cameraLauncher.launch(null) }) {
                    Text(text = "Take picture")
                }
            } else {
                Text(text = "Camera permission is required")
                Spacer(modifier = modifier.height(16.dp))
                Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                    Text(text = "request camera permission")
                }
            }
            Spacer(modifier = modifier.height(16.dp))

            Button(onClick = {
                val intent = Intent(Intent.ACTION_PICK).apply { type = "image/*" }
                galleryLauncher.launch(intent)
            }) {
                Text(text = "pick image from gallery")
            }
            Spacer(modifier = modifier.height(16.dp))
            imageUri?.let {
                val bitmap = if (Build.VERSION.SDK_INT < 28) {
                    MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, it)
                    ImageDecoder.decodeBitmap(source)
                }
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = null,
                    modifier = modifier.size(200.dp)
                )
            }
        }
    }

    fun saveImageToInternalStorage(context: Context, bitmap: Bitmap): Uri? {
        val filename = "photo_${System.currentTimeMillis()}.jpg"
        val outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.close()
        return Uri.fromFile(context.getFileStreamPath(filename))
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
}