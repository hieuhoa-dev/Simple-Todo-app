package com.example.learnkotlin.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Todo::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TodoDatabase: RoomDatabase() {
    companion object {
        const val DB_NAME = "todo_db"
    }

    abstract fun getTodoDao(): TodoDao
}