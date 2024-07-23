package com.gun.course

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gun.course.ui.theme.CourseAppTheme

class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CourseAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    StatelessFun(name = "Budi", modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!", modifier = modifier
    )
}

@Composable
fun MyButton(modifier: Modifier = Modifier) {
    Button(onClick = { /*TODO*/ }) {
        Text("Click me")
    }
}

@Preview(showBackground = true)
@Composable
private fun MyButtonPreview() {
    MyButton()
}

// stateles fun

@Composable
fun StatelessFun(name: String, modifier: Modifier = Modifier) {
    Text(text = "Hello $name", modifier = modifier)
}

// stateful fun
@Composable
fun StatefulFun(modifier: Modifier = Modifier) {
    val name by remember {
        mutableStateOf("world")
    } // state internal
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
private fun StateFulPreview() {
    StatefulFun()
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CourseAppTheme {
        Greeting("Android")
    }
}