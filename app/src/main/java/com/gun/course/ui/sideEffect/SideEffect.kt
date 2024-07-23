package com.gun.course.ui.sideEffect

import android.location.LocationListener
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gun.course.ui.theme.CourseAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// launchedEffect

@Composable
fun DataFetcher(modifier: Modifier = Modifier) {
    var data by remember {
        mutableStateOf("Loading")
    } // state
    LaunchedEffect(Unit) {
        data = fetchDataFromNetwork()
    }
    Text(text = data)
}


@Composable
fun LocationTracker(modifier: Modifier = Modifier) {
//    DisposableEffect(Unit) {
//        val locationListener = LocationListener {
//            //
//        }
////        startTrackingLocation(locationListener)
////        //end of recompose
////        onDispose {
////            stopTrackingLocation(locationListener)
////        }
//    }
}


// rememberScope

@Composable
fun FetchButton(modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    var data by remember { mutableStateOf("Initial Text") }
    Column {
        Button(onClick = { scope.launch { data = fetchDataFromNetwork() } }) {
            Text(text = "Fetch Data")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = data)
    }
}

suspend fun fetchDataFromNetwork(): String {
    delay(2000)
    return "Hello, compose here"
}

@Preview
@Composable
private fun PreviewSideEffect() {
    CourseAppTheme {
        FetchButton()
    }
}
