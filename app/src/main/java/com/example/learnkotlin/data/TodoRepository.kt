package com.example.learnkotlin.data

import com.example.learnkotlin.api.RetrofitInstance

import com.example.learnkotlin.api.toLocalList
import java.time.Instant
import java.util.Date

class TodoRepository(private val todoDao: TodoDao) {

    val allTodos = todoDao.getAllTodo()

    suspend fun refreshTodos() {
        val todosFromApi = RetrofitInstance.api.getTodoData().toLocalList()
        todoDao.insertAll(todosFromApi)
    }

    suspend fun addTodo(title: String) {
        todoDao.addTodo(Todo(title = title, createAt = Date.from(Instant.now())))
    }

    suspend fun deleteTodo(id: Int) {
        todoDao.deleteTodo(id)
    }
}