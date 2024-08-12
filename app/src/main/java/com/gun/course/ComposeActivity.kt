package com.gun.course

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gun.course.ui.theme.CourseAppTheme
import com.gun.course.utils.IS_LOGIN_KEY
import com.gun.course.utils.USERNAME_KEY
import com.gun.course.utils.dataStore
import com.gun.course.utils.getSharedPreference
import com.gun.course.utils.savePreference
import com.gun.course.utils.saveToDataStore
import com.gun.course.viewmodel.UserViewModel
import com.gun.course.viewmodel.UserViewModelFactory
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            CourseAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    UserListScreen(modifier = Modifier.padding(innerPadding))
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


@Composable
fun UserListScreen(
    modifier: Modifier = Modifier, userViewModel: UserViewModel = viewModel(
        factory = UserViewModelFactory(
            LocalContext.current
        )
    )
) {

    val names by userViewModel.name.collectAsState()
    var newName by remember {
        mutableStateOf("")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(modifier = modifier.weight(1f)) {
            items(names) { name ->
                Text(text = name)
            }
        }
        OutlinedTextField(
            value = newName,
            onValueChange = { newName = it },
            label = { Text(text = "Enter name") }, modifier = modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            if (newName.isNotEmpty()) {
                userViewModel.addName(newName)
                newName = ""
            }
        }, modifier = modifier.fillMaxWidth()) {
            Text(text = "Add Name")
        }
    }
    LaunchedEffect(Unit) {
        userViewModel.loadNames()
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CourseAppTheme {
        UserListScreen()
    }
}