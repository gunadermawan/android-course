package com.gun.course

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gun.course.ui.theme.CourseAppTheme
import com.gun.course.utils.IS_LOGIN_KEY
import com.gun.course.utils.USERNAME_KEY
import com.gun.course.utils.dataStore
import com.gun.course.utils.getSharedPreference
import com.gun.course.utils.savePreference
import com.gun.course.utils.saveToDataStore
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CourseAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DataStoreScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun DataStoreScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var username by remember {
        mutableStateOf("")
    }
    val isLoggedIn by remember {
        mutableStateOf(false)
    }

    val userPreferences by context.dataStore.data
        .map {
            Pair(
                it[USERNAME_KEY] ?: "",
                it[IS_LOGIN_KEY] ?: false
            )
        }.collectAsState(initial = Pair("", false))
    Column(modifier = modifier.padding(16.dp)) {
        Text(text = "userName: ${userPreferences.first}")
        Text(text = "isLoggedIn: ${userPreferences.second}")
        Spacer(modifier = modifier.height(8.dp))
        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
            },
        )
        Spacer(modifier = modifier.height(8.dp))
        Button(onClick = { coroutineScope.launch { saveToDataStore(context, username, true) } }) {
            Text(text = "Save to Datastore")
        }
    }
}

@Composable
fun SharedPrefScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var userName by remember {
        mutableStateOf("")
    }
    val isLoggedIn by remember {
        mutableStateOf(false)
    }
    val (savedUserName, savedIsLoggedIn) = getSharedPreference(context)

    Column(modifier = modifier.padding(16.dp)) {
        Text(text = "userName: $userName")
        Text(text = "isLoggedIn: $isLoggedIn")
        OutlinedTextField(
            value = userName,
            onValueChange = {
                userName = it
            },
            label = { Text(text = "Enter user name") }
        )
        Spacer(modifier = modifier.height(8.dp))
        Button(onClick = { savePreference(context, userName, true) }) {
            Text(text = "Save Preferences")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CourseAppTheme {
        DataStoreScreen()
    }
}