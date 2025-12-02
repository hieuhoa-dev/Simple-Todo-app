package com.example.learnkotlin.ui.page

import java.time.Instant
import java.util.Date

data class ToDo(
    var id: Int,
    var title: String,
    var createAt: Date
)

fun getFakeToDoList(): List<ToDo> {
    return listOf(
        ToDo(1, "First", Date.from(Instant.now())),
        ToDo(2, "Second", Date.from(Instant.now())),
        ToDo(3, "Third", Date.from(Instant.now())),
        ToDo(4, "Fourth", Date.from(Instant.now()))
    )
}