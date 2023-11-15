package com.guilherme.tasks.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0,
    val title: String,
    val body: String,
    val price: Double?,
    val date: Long?
)