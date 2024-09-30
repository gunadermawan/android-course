package com.gun.course

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.gun.course.dao.UserDao
import com.gun.course.db.AppDatabase
import com.gun.course.model.Task
import com.gun.course.model.User
import com.gun.course.model.UserWithTask
import com.gun.course.receiver.GeofenceBroadcastReceiver
import com.gun.course.ui.theme.CourseAppTheme
import com.gun.course.utils.GeofenceHelper
import kotlinx.coroutines.launch

class ComposeActivity : ComponentActivity() {
    private lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            CourseAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    userDao = AppDatabase.getInstance(application).userDao()
                    UserTaskScreen(
                        userDao,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    class UserViewModel(private val userDao: UserDao) : ViewModel() {
        var userWithTask by mutableStateOf<UserWithTask?>(null)

        fun addUser(userName: String, age: Int?) {
            viewModelScope.launch {
                try {
                    val user = User(userName = userName, age = age)
                    userDao.insertUser(user)
                    Log.d("UserViewModel", "User inserted successfully: $userName with age: $age")
                    loadUserWithTasks(user.userId)
                } catch (e: Exception) {
                    Log.e("UserViewModel", "Error inserting user", e)
                }
            }
        }

        fun addTaskForUser(userId: Long, taskName: String) {
            viewModelScope.launch {
                try {
                    val task = Task(taskName = taskName, userOwnerId = userId)
                    userDao.insertTask(task)
                    Log.d("UserViewModel", "Task inserted successfully for user $userId: $taskName")
                    loadUserWithTasks(userId)
                } catch (e: Exception) {
                    Log.e("UserViewModel", "Error inserting task", e)
                }
            }
        }

        private fun loadUserWithTasks(userId: Long) {
            viewModelScope.launch {
                try {
                    userWithTask = userDao.getUserWithTask(userId)
                    Log.d("UserViewModel", "User with tasks loaded successfully: $userWithTask")
                } catch (e: Exception) {
                    Log.e("UserViewModel", "Error loading user with tasks", e)
                }
            }
        }
    }

    class UserViewModelFactory(private val userDao: UserDao) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
                return UserViewModel(userDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    @Composable
    fun UserTaskScreen(userDao: UserDao, modifier: Modifier = Modifier) {
        val userViewModel: UserViewModel = viewModel(factory = UserViewModelFactory(userDao))

        val userWithTask = userViewModel.userWithTask

        var userName by remember { mutableStateOf("") }
        var userAge by remember { mutableStateOf("") }
        var taskName by remember { mutableStateOf("") }
        Column(modifier = modifier.padding(16.dp)) {
            TextField(
                value = userName,
                onValueChange = { userName = it },
                label = { Text(text = "User Name") })

            TextField(
                value = userAge,
                onValueChange = { userAge = it },
                label = { Text(text = "User Age") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Button(onClick = {
                val age = userAge.toIntOrNull()
                userViewModel.addUser(userName, age)
            }) {
                Text(text = "Add User")
            }
            TextField(
                value = taskName,
                onValueChange = { taskName = it },
                label = { Text("Task Name") }
            )
            Button(onClick = { userViewModel.addTaskForUser(1, taskName) }) {
                Text(text = "Add Task")
            }
            userWithTask?.let { userWithTask ->
                Text(text = "User: ${userWithTask.user.userName}")
                Text(text = "User Age: ${userWithTask.user.age ?: "N/A"}")
                Text(text = "Task")
                userWithTask.tasks.forEach { task ->
                    Text(text = "- ${task.taskName}")
                }
            }
        }
    }
}