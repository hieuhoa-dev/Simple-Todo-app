package com.example.learnkotlin.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.util.Date

@Entity
data class Todo(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var title: String,
    var createAt: Date,
    var orderIndex: Int = 0
)

fun getFakeTodoList(): List<Todo> {
    return listOf(
        Todo(1, "First", Date.from(Instant.now()), 0),
        Todo(2, "Second", Date.from(Instant.now()), 1),
        Todo(3, "Third", Date.from(Instant.now()), 2),
        Todo(4, "Fourth", Date.from(Instant.now()), 3)
    )
}