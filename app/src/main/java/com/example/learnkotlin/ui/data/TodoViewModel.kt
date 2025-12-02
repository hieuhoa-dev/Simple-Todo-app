package com.example.learnkotlin.ui.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.learnkotlin.ui.page.ToDo

class TodoViewModel : ViewModel() {
    private val _todoList = MutableLiveData<List<ToDo>>()
    val todoList: LiveData<List<ToDo>> = _todoList

    fun getAllTodo() {
        _todoList.value = TodoManager.getAllTodo().sortedByDescending { it.createAt }
    }

    fun addTodo(title: String) {
        TodoManager.addTodo(title)
        getAllTodo()
    }

    fun deleteTodo(Id: Int) {
        TodoManager.deleteTodo(Id)
        getAllTodo()
    }
}