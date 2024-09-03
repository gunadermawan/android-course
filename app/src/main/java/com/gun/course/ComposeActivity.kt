package com.gun.course

import android.Manifest
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.gun.course.ui.theme.CourseAppTheme

class ComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CourseAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RequestPermissionScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
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


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun SidebarContentLayout(
    modifier: Modifier = Modifier,
    items: List<String>,
    onItemSelected: (String) -> Unit
) {

    val selectedItem = remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val activity = context as? Activity ?: return
    val windowSize = calculateWindowSizeClass(activity = activity)

    when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            Column(modifier = modifier.fillMaxSize()) {
                Sidebar(items = items, onItemSelected = {
                    selectedItem.value = it
                    onItemSelected(it)
                })
                selectedItem.value?.let {
                    ContentDetails(
                        item = it,
                        modifier = modifier.weight(3f)
                    )
                }
            }
        }

        WindowWidthSizeClass.Expanded -> {
            Row {
                Sidebar(items = items, onItemSelected = {
                    selectedItem.value = it
                    onItemSelected(it)
                })
                selectedItem.value?.let {
                    ContentDetails(
                        item = it,
                        modifier = modifier.weight(3f)
                    )
                }
            }
        }
    }
}

@Composable
fun Sidebar(
    modifier: Modifier = Modifier,
    items: List<String>,
    onItemSelected: (String) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(items) { item ->
            Text(
                text = item,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable { onItemSelected(item) })
        }
    }
}

@Composable
fun ContentDetails(modifier: Modifier = Modifier, item: String) {
    Box(modifier = modifier.padding(16.dp)) {
        Text(text = "Detail item $item")
    }
}
