package com.gun.course.ui.sideEffect

import android.location.LocationListener
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.tooling.preview.Preview
import com.gun.course.ui.theme.CourseAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// launchedEffect
@Composable
fun DataFetcher(modifier: Modifier = Modifier) {
    var data by remember { mutableStateOf("Loading data") } // init state
    LaunchedEffect(Unit) {
        data = fetchDataFromNetwork()
    }
    Text(text = data)
}


// disposableEffect
@Composable
fun LocationTracking(modifier: Modifier = Modifier) {
    DisposableEffect(Unit) {
        val locationListener = LocationListener {
            // handle locationlistener
        }
//        startTracking(locationListener)
        onDispose {
//            stopTracking(locationListener)
        }
    }
}

// rememberCoroutineScope
@Composable
fun FetchButton(modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    Button(onClick = {
        scope.launch {
            val data = fetchDataFromNetwork()
            return@launch
        }
    }) {
        Text(text = "Fetch data")
    }
}

suspend fun fetchDataFromNetwork(): String {
    delay(2000)
    return "Hello, compose here!"
}

@Preview
@Composable
private fun DataFetcherPreview() {
    CourseAppTheme {
//        DataFetcher()
        FetchButton()
    }
}
