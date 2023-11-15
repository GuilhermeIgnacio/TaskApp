package com.guilherme.tasks.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Delete
import androidx.room.DeleteTable
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import com.guilherme.tasks.dao.TaskDao
import com.guilherme.tasks.models.Task

//@Database(entities = [Task::class], version = 2, exportSchema = true, autoMigrations = [AutoMigration(from = 1, to = 2)])
@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao
}