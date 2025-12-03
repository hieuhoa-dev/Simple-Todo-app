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
        val currentTodos = todoDao.getAllTodoSync()
        val nextOrderIndex = (currentTodos.maxByOrNull { it.orderIndex }?.orderIndex ?: -1) + 1
        todoDao.addTodo(Todo(title = title, createAt = Date.from(Instant.now()), orderIndex = nextOrderIndex))
    }

    suspend fun deleteTodo(id: Int) {
        todoDao.deleteTodo(id)
    }

    suspend fun reorderTodos(fromIndex: Int, toIndex: Int) {
        val todos = todoDao.getAllTodoSync()

        if (todos.isNotEmpty() && fromIndex < todos.size && toIndex < todos.size && fromIndex != toIndex) {
            val newTodos = todos.toMutableList()
            val todoToMove = newTodos.removeAt(fromIndex)
            newTodos.add(toIndex, todoToMove)

            // Update order indices
            newTodos.forEachIndexed { index, todo ->
                todo.orderIndex = index
            }

            // Update all todos with new order indices
            todoDao.insertAll(newTodos)
        }
    }
}