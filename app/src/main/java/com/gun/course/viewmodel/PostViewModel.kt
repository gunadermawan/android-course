package com.gun.course.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.gun.course.model.Post
import com.gun.course.repository.PostRepo
import kotlinx.coroutines.launch

class PostViewModel(private val repo: PostRepo) : ViewModel() {
    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> = _posts

    init {
        fetchPosts()
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            try {
                val response = repo.getPosts()
                _posts.value = response
            } catch (e: Exception) {
                _posts.value = emptyList()
            }
        }
    }
}

class PostViewModelFactory(private val repository: PostRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
            return PostViewModel(repository) as T
        }
        throw IllegalArgumentException("unknown view model class")
    }
}