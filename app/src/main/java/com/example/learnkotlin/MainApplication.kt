package com.example.learnkotlin

import android.app.Application
import androidx.room.Room
import com.example.learnkotlin.data.TodoDatabase

class MainApplication : Application() {

    companion object {
        lateinit var todoDatabase: TodoDatabase
    }

    override fun onCreate() {
        super.onCreate()
        todoDatabase = Room.databaseBuilder(
            applicationContext,
            TodoDatabase::class.java,
            TodoDatabase.DB_NAME
        ).build()
    }
}