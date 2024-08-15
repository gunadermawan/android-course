package com.gun.course

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gun.course.model.Post
import com.gun.course.network.RetrofitInstance
import com.gun.course.repository.PostRepo
import com.gun.course.ui.theme.CourseAppTheme
import com.gun.course.viewmodel.PostViewModel
import com.gun.course.viewmodel.PostViewModelFactory
import kotlinx.coroutines.launch

class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel(this)
//        enableEdgeToEdge()
        setContent {
            CourseAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NetworkRequest(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Channel Name"
            val descText = "Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
                description = descText
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}

@Composable
fun NotificationScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var hasNotificationPermission by remember {
        mutableStateOf(chekcNotificationPermission(context))
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasNotificationPermission = isGranted
            if (isGranted) {
                showNotification(context)
            }
        }
    )
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (!hasNotificationPermission) {
                    launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
                } else {
                    showNotification(context)
                }
            } else {
                showNotification(context)
            }
        }) {
            Text(text = "Show Notification")
        }
        Spacer(modifier = modifier.height(16.dp))
        Text(text = if (hasNotificationPermission) "Notification Permission Granted" else "Notification Permission Not Granted")
    }
}

fun chekcNotificationPermission(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    } else {
        true
    }
}

fun showNotification(context: Context) {
    val notificationId = 1
    val builder = NotificationCompat.Builder(context, "CHANNEL_ID")
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle("New Notification")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    with(NotificationManagerCompat.from(context)) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        notify(notificationId, builder.build())
    }
}


@Composable
fun NetworkRequest(modifier: Modifier = Modifier) {
    val context = LocalContext.current
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
                    showNotification(context)
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

@Composable
fun PostScreen(
    modifier: Modifier = Modifier,
    postViewModel: PostViewModel = viewModel(
        factory = PostViewModelFactory(
            PostRepo(RetrofitInstance.api)
        )
    )
) {
    val posts by postViewModel.posts.observeAsState(emptyList())
    LazyColumn {
        items(posts.size) { index ->
            val post = posts[index]
            PostItem(post = post)
        }
    }
}

@Composable
fun PostItem(post: Post) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = post.title, style = MaterialTheme.typography.titleMedium)
        Text(text = post.body, style = MaterialTheme.typography.bodyMedium)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CourseAppTheme {
        PostScreen()
    }
}