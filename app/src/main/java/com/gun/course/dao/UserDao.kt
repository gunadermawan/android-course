package com.gun.course.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Transaction
import androidx.sqlite.db.SupportSQLiteQuery
import com.gun.course.model.Task
import com.gun.course.model.User
import com.gun.course.model.UserWithTask

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Transaction
    @Query("SELECT * FROM user_table WHERE userId = :userId")
    suspend fun getUserWithTask(userId: Long): UserWithTask

    @RawQuery
    suspend fun getUserWithAgeGreaterThan(query: SupportSQLiteQuery): List<User>

    @Query("SELECT * FROM  user_table ORDER BY userName ASC")
    suspend fun getUserSortedByName(): List<User>

    @Query("SELECT * FROM  user_table ORDER BY userName DESC")
    suspend fun getUserSortedByNameDesc(): List<User>

    @Query("SELECT * FROM  user_table ORDER BY age ASC")
    suspend fun getUserSortedByAge(): List<User>

    @Query("SELECT * FROM  user_table ORDER BY age DESC")
    suspend fun getUserSortedByAgeDesc(): List<User>


}