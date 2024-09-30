package com.gun.course.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true) val userId: Long = 0,
    val userName: String,
    val age: Int? = null
)

@Entity(
    tableName = "task_table",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["userId"],
        childColumns = ["userOwnerId"]
    )]
)

data class Task(
    @PrimaryKey(autoGenerate = true) val taskId: Long = 0,
    val taskName: String,
    val userOwnerId: Long
)
