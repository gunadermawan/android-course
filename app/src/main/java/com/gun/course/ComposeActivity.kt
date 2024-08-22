package com.gun.course

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gun.course.custom.MyCustomLayout
import com.gun.course.ui.theme.CourseAppTheme

class ComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            CourseAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CustomLayoutScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        CourseAppTheme {
            CustomLayoutScreen(modifier = Modifier)
        }
    }
}

@Composable
fun CustomLayoutScreen(modifier: Modifier = Modifier) {
    MyCustomLayout(modifier = modifier
        .padding(16.dp)) {
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

