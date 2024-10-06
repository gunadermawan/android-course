package com.gun.course

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.sqlite.db.SimpleSQLiteQuery
import com.gun.course.dao.UserDao
import com.gun.course.db.AppDatabase
import com.gun.course.model.Task
import com.gun.course.model.User
import com.gun.course.model.UserWithTask
import com.gun.course.ui.theme.CourseAppTheme
import com.gun.course.utils.CustomAlertDialog
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
                    ShowDialogScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    class UserViewModel(private val userDao: UserDao) : ViewModel() {
        var userWithTask by mutableStateOf<UserWithTask?>(null)
        var userAgeAbove by mutableStateOf<List<User>>(emptyList())
        var sortedUsers by mutableStateOf<List<User>?>(null)

        val pagedUsers = Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { userDao.getUsersPaging() }).flow.cachedIn(viewModelScope)

        fun loadUserSortedByAsc() {
            viewModelScope.launch {
                try {
                    sortedUsers = userDao.getUserSortedByName()
                } catch (e: Exception) {
                    Log.e("UserViewModel", "Error loading users sorted by age", e)
                }
            }
        }

        fun loadUserSortedByDesc() {
            viewModelScope.launch {
                try {
                    sortedUsers = userDao.getUserSortedByAgeDesc()
                } catch (e: Exception) {
                    Log.e("UserViewModel", "Error loading users sorted by age", e)
                }
            }
        }

        fun loadUserSortedByAgeAsc() {
            viewModelScope.launch {
                try {
                    sortedUsers = userDao.getUserSortedByAge()
                } catch (e: Exception) {
                    Log.e("UserViewModel", "Error loading users sorted by age", e)
                }
            }
        }


        fun getUserAgeAbove(age: Int) {
            viewModelScope.launch {
                val query =
                    SimpleSQLiteQuery("SELECT * FROM user_table WHERE age > ?", arrayOf(age))
                userAgeAbove = userDao.getUserWithAgeGreaterThan(query)
                Log.d("UserViewModel", "Users with age greater than: $userAgeAbove")
            }
        }

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
        val pagedUsers = userViewModel.pagedUsers.collectAsLazyPagingItems()

        val userWithTask = userViewModel.userWithTask
        val userAgeAbove = userViewModel.userAgeAbove

        var userName by remember { mutableStateOf("") }
        var userAge by remember { mutableStateOf("") }
        var taskName by remember { mutableStateOf("") }
        var minAge by remember { mutableStateOf("") }
        val sortedUsers = userViewModel.sortedUsers

        Column(modifier = modifier.padding(16.dp)) {
            LazyColumn {
                items(pagedUsers.itemCount) { index ->
                    val user = pagedUsers[index]
                    user?.let {
                        Text(text = "User: ${it.userName}, Age: ${it.age}")
                    }
                }
                pagedUsers.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item { CircularProgressIndicator() }
                        }

                        loadState.append is LoadState.Loading -> {
                            item { CircularProgressIndicator() }
                        }

                        loadState.refresh is LoadState.Error -> {
                            item {
                                Text(text = "Error loading data: ${loadState.refresh as LoadState.Error}")
                            }
                        }

                        loadState.append is LoadState.Error -> {
                            item {
                                Text(text = "Error loading data: ${loadState.append as LoadState.Error}")
                            }
                        }
                    }
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
}