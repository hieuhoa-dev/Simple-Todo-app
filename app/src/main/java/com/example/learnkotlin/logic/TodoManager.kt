package com.example.learnkotlin.logic

import com.example.learnkotlin.data.Todo
import java.time.Instant
import java.util.Date

// Old not use
object TodoManager {
    private val TodoList = mutableListOf<Todo>()

    fun getAllTodo(): List<Todo> {
        return TodoList
    }

    fun addTodo(title: String) {
        TodoList.add(
            Todo(
                System.currentTimeMillis().toInt(),
                title,
                Date.from(Instant.now())
            )
        )
    }

    fun deleteTodo(Id: Int) {
        TodoList.removeIf {
            it.id == Id
        }
    }
}