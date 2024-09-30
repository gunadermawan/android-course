package com.gun.course.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.gun.course.dao.UserDao
import com.gun.course.model.Task
import com.gun.course.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(entities = [User::class, Task::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).addCallback(AppDatabaseCallback(context))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class AppDatabaseCallback(private val context: Context) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let {
                CoroutineScope(Dispatchers.IO).launch {
                    prePopulateData(it.userDao())
                }
            }
        }

        suspend fun prePopulateData(userDao: UserDao) {
            val user = User(userName = "pre-populate user")
            val userId = userDao.insertUser(user)
            val task1 =
                Task(taskName = "pre-populate task 1", userOwnerId = userId)
            val task2 =
                Task(taskName = "pre-populate task 2", userOwnerId = userId)
            userDao.insertTask(task1)
            userDao.insertTask(task2)
        }
    }

}

