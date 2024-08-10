package com.gun.course.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gun.course.model.Article

class ArticleViewModel : ViewModel() {
    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> get() = _articles

    private val _data = MutableLiveData<String>()
    val data: LiveData<String> get() = _data

    fun fetchArticles() {
        _articles.value = listOf(
            Article("Title 1", "Content 1"),
            Article("Title 2", "Content 2"),
            Article("Title 3", "Content 3"),
            Article("Title 4", "Content 4"),
            Article("Title 5", "Content 5"),
            Article("Title 6", "Content 6"),
            Article("Title 7", "Content 7"),
            Article("Title 8", "Content 8"),
            Article("Title 9", "Content 9"),
            Article("Title 10", "Content 10"),
        )
    }

    fun updateData(newData: String) {
        _data.value = newData
    }
}