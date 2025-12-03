package com.example.learnkotlin.api

import com.example.learnkotlin.data.Todo
import retrofit2.http.GET
import java.util.Date

interface TodoApi {
    @GET("todos")
    suspend fun getTodoData(): TodoResponse
}

data class TodoResponse(
    val todos: List<TodoApiModel>,
    val total: Int,
    val skip: Int,
    val limit: Int
)

data class TodoApiModel(
    val id: Int,
    val todo: String,
    val completed: Boolean,
    val userId: Int
)

fun TodoApiModel.toLocalTodo(): Todo {
    return Todo(
        id = 0,
        title = this.todo,
        createAt = Date()
    )
}

fun TodoResponse.toLocalList(): List<Todo> {
    return todos.map { it.toLocalTodo() }
}