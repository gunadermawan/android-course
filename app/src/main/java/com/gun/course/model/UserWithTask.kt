package com.gun.course.model

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithTask(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userOwnerId"
    ) val tasks: List<Task>
)
