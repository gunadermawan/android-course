package com.gun.course.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val auth: FirebaseAuth) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState = _authState.asStateFlow()
    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    _authState.value = if (task.isSuccessful) {
                        AuthState.Success("Login Successful!")
                    } else {
                        AuthState.Failure(task.exception?.message ?: "Login Failed")
                    }
                }
        }
    }

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    _authState.value = if (task.isSuccessful) {
                        AuthState.Success("Registration Successful!")
                    } else {
                        AuthState.Failure(task.exception?.message ?: "Registration Failed!")
                    }
                }
        }
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val message: String) : AuthState()
    data class Failure(val message: String) : AuthState()
}