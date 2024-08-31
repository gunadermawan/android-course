package com.gun.course

import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.gun.course.ui.theme.CourseAppTheme
import kotlin.math.exp

class ComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CourseAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyAnimateVisibility(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun CustomGraphics(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.size(200.dp)) {
        drawCircle(
            color = Color.Red,
            radius = size.minDimension / 2,
            center = Offset(size.width / 2, size.height / 2)
        )
    }
}

@Composable
fun CustomShape(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(100.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(color = Color.Red)
    )
}


@Composable
fun GradientBrushSample(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(100.dp)
            .background(Brush.linearGradient(colors = listOf(Color.Red, Color.Blue)))
    )
}

@Composable
fun WebViewComponent(url: String) {
    AndroidView(factory = { context ->
        WebView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = WebViewClient()
            loadUrl(url)
        }
    }, update = { webview ->
        webview.loadUrl(url)
    })
}

@Composable
fun WebViewSample(modifier: Modifier = Modifier) {
    var url by remember { mutableStateOf("https://www.google.com") }

    Column {
        Button(onClick = { url = "https://www.google.com" }) {
            Text(text = "load another website")
        }
        // WebView Component
        WebViewComponent(url = url)
    }
}

@Composable
fun ExpandableCard(modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    Card(modifier = modifier
        .fillMaxWidth()
        .padding(16.dp)
        .clickable { expanded = !expanded }
        .animateContentSize()) {
        Column(modifier = modifier.padding(16.dp)) {
            Text(text = "Judul Card")
            if (expanded) {
                Text(text = "konten yang muncul saat card di klik")
            }
        }
    }
}

@Composable
fun MyAnimateVisibility(modifier: Modifier = Modifier) {
    var visible by remember { mutableStateOf(true) }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(visible = visible, enter = scaleIn(), exit = scaleOut()) {
            Text(text = "Teks ini akan muncul dan menghilang dengan animasi visibility")
        }
        Spacer(modifier = modifier.height(16.dp))
        Button(onClick = { visible = !visible }) {
            Text(text = "Toggle Visibility")
        }
    }
}


@Preview
@Composable
private fun GraphicsPreview() {
    CourseAppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomGraphics()
            CustomShape()
            GradientBrushSample()
        }
    }
}

