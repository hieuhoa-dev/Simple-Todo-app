package com.example.learnkotlin.ui.data

import com.example.learnkotlin.ui.page.ToDo
import java.time.Instant
import java.util.Date

object TodoManager {
    private val TodoList = mutableListOf<ToDo>()

    fun getAllTodo(): List<ToDo> {
        return TodoList
    }

    fun addTodo(title: String) {
        TodoList.add(
            ToDo(
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
