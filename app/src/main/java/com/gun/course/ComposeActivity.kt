package com.gun.course

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.gun.course.ui.theme.CourseAppTheme
import com.gun.course.viewmodel.UserViewmodel

class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userViewmodel: UserViewmodel by viewModels()
//        enableEdgeToEdge()
        setContent {
            CourseAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    UserProfileScreen(
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
    fun UserProfileScreen(modifier: Modifier = Modifier, viewmodel: UserViewmodel) {
        val user by viewmodel.user.collectAsState()
        Column(modifier = modifier.fillMaxSize()) {
            Text(text = "Name: ${user.name}")
            Text(text = "Email: ${user.email}")
        }
    }
}