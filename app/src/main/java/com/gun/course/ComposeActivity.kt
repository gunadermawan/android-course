package com.gun.course

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gun.course.custom.MyCustomLayout
import com.gun.course.ui.theme.CourseAppTheme

class ComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CourseAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AdvancedImageScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        CourseAppTheme {
            AdvancedImageScreen(modifier = Modifier)
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AdvancedImageScreen(modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    HorizontalPager(
        state = pagerState,
        modifier = modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) { page ->
        when (page) {
            0 -> {
                val bitmap = ImageBitmap.imageResource(id = R.drawable.img_1)
                Image(
                    bitmap = bitmap,
                    contentDescription = "image bitmap",
                    modifier = modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .border(
                            BorderStroke(4.dp, Color.Gray),
                            RoundedCornerShape(16.dp)
                        )
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            1 -> {
                val vector = ImageVector.vectorResource(id = R.drawable.baseline_back_hand_24)
                Image(
                    imageVector = vector, contentDescription = "image vector", modifier = modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .border(
                            BorderStroke(4.dp, Color.Gray),
                            RoundedCornerShape(16.dp)
                        )
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            2 -> {
                AsyncImage(
                    model = "https://picsum.photos/id/133/2742/1828",
                    contentDescription = "image from internet",
                    modifier = modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .border(
                            BorderStroke(4.dp, Color.Gray),
                            RoundedCornerShape(16.dp)
                        )
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun CustomLayoutScreen(modifier: Modifier = Modifier) {
    MyCustomLayout(
        modifier = modifier
            .padding(16.dp)
    ) {
        Text(text = "item 1")
        Text(text = "item 2")
        Text(text = "item 3")
    }
}

@Composable
fun GreetingScreen(modifier: Modifier) {

    var name by remember {
        mutableStateOf("")
    }

    var greeting by remember {
        mutableStateOf("")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(text = "Enter your name") })
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { greeting = "Hello, $name" }) {
            Text(text = "Greet")
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (greeting.isNotEmpty()) {
            Text(text = greeting)
        }
    }
}

