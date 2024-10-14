package com.gun.course

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gun.course.network.RetrofitInstance
import com.gun.course.ui.theme.CourseAppTheme
import com.gun.course.viewmodel.UserViewmodel

class ComposeActivity : ComponentActivity() {
    private val apiService = RetrofitInstance.api
//    private val userRepository = UserRepoImpl(apiService)
//    private val getUserUseCase = GetUserUseCase(userRepository)
    private val userViewmodel by lazy { UserViewmodel(apiService) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            CourseAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    UserListScreen(
                        modifier = Modifier.padding(innerPadding),
                        viewmodel = userViewmodel
                    )
                }
            }
        }
    }


    @Composable
    fun ShowDialogScreen(modifier: Modifier = Modifier) {
        var showDialog by remember {
            mutableStateOf(false)
        }
        Column {
            Button(onClick = { showDialog = true }) {
                Text(text = "show dialog")
            }

            if (showDialog) {
                CustomAlertDialog()
            }
        }
    }

    @Composable
    fun CustomAlertDialog(modifier: Modifier = Modifier) {
        var showDialog by remember {
            mutableStateOf(true)
        }
        if (showDialog) {
            AlertDialog(onDismissRequest = {
                showDialog = false
            }, title = {
                Text(text = "Peringatan")
            }, text = {
                Text(text = "Apakah ingin melanjutkan proses?")
            }, confirmButton = {
                Button(onClick = {
                    showDialog = false
                }) {
                    Text(text = "Ya")
                }
            }, dismissButton = {
                Button(onClick = {
                    showDialog = false
                }) {
                    Text(text = "Tidak")
                }
            }
            )
        }
    }


    @Composable
    fun UserListScreen(modifier: Modifier = Modifier, viewmodel: UserViewmodel) {
        val users by viewmodel.users.collectAsState()
        val error by viewmodel.error.collectAsState()
        LaunchedEffect(Unit) {
            viewmodel.fetchUsers()
        }
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(users) {
                Text(text = it.name)
            }
        }
    }
}