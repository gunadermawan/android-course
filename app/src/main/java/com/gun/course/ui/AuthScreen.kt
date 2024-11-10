package com.gun.course.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gun.course.viewmodel.AuthState
import com.gun.course.viewmodel.AuthViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun AuthScreen(modifier: Modifier, viewModel: AuthViewModel = koinViewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState by viewModel.authState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email") },
            modifier = modifier.fillMaxWidth()
        )
        Spacer(modifier = modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") },
            modifier = modifier.fillMaxWidth()
        )
        Spacer(modifier = modifier.height(8.dp))
        Button(
            onClick = { viewModel.signIn(email, password) },
            modifier = modifier.fillMaxWidth()
        ) {
            Text(text = "Login")
        }
        Spacer(modifier = modifier.height(8.dp))
        Button(
            onClick = { viewModel.signUp(email, password) },
            modifier = modifier.fillMaxWidth()
        ) {
            Text(text = "Register")
        }
        Spacer(modifier = modifier.height(8.dp))
        when (authState) {
            is AuthState.Loading -> CircularProgressIndicator()
            is AuthState.Success -> Text(text = (authState as AuthState.Success).message)
            is AuthState.Failure -> Text(
                text = (authState as AuthState.Failure).message,
                color = MaterialTheme.colorScheme.error
            )

            else -> {}
        }

    }
}