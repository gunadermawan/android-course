package com.gun.course

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gun.course.model.Post
import com.gun.course.network.RetrofitInstance
import com.gun.course.ui.theme.CourseAppTheme
import kotlinx.coroutines.launch

class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CourseAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NetworkRequest(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}


@Composable
fun NetworkRequest(modifier: Modifier = Modifier) {
    var posts by remember {
        mutableStateOf<List<Post>>(emptyList())
    }
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(posts) { post ->
                Text(text = post.title, modifier = Modifier.padding(bottom = 16.dp))
            }
        }
        Button(onClick = {
            scope.launch {
                try {
                    val post = RetrofitInstance.api.getPost()
                    posts = post.take(10)
                    Log.d("TAG DEBOS", "NetworkRequest: enter try")
                } catch (e: Exception) {
                    Log.e("TAG ERROR", "NetworkRequest: ${e.message}")
                }
            }
        }) {
            Text(text = "get data")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CourseAppTheme {
        NetworkRequest()
    }
}