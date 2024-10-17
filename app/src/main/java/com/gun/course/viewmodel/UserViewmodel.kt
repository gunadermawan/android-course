package com.gun.course.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gun.course.model.User
import com.gun.course.network.ApiService
import com.gun.course.repository.UserRepository
import com.gun.course.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewmodel @Inject constructor(private val apiService: ApiService) : ViewModel() {
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val compositeDisposable = CompositeDisposable()

    fun fetchUsers() {
        viewModelScope.launch {
            flow {
                emit(apiService.getUser())
            }.catch { e ->
                _error.value = e.message
            }
                .collect {
                    _users.value = it
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}