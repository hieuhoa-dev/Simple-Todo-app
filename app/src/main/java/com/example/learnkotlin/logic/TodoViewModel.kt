package com.example.learnkotlin.logic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnkotlin.MainApplication
import com.example.learnkotlin.data.Todo
import com.example.learnkotlin.data.TodoDao
import com.example.learnkotlin.logic.TodoManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date

class TodoViewModel : ViewModel() {
//    private val _todoList = MutableLiveData<List<Todo>>()
//    val todoList: LiveData<List<Todo>> = _todoList

    val todoDao = MainApplication.todoDatabase.getTodoDao()
    val todoList: LiveData<List<Todo>> = todoDao.getAllTodo()


//    fun getAllTodo() {
//        _todoList.value = TodoManager.getAllTodo().sortedByDescending { it.createAt }
//    }


    fun addTodo(title: String) {
//        TodoManager.addTodo(title)
//        getAllTodo()

        viewModelScope.launch(Dispatchers.IO) {
            todoDao.addTodo(Todo(title = title, createAt = Date.from(Instant.now())))
        }
    }

    fun deleteTodo(Id: Int) {
//        TodoManager.deleteTodo(Id)
//        getAllTodo()

        viewModelScope.launch(Dispatchers.IO) {
            todoDao.deleteTodo(Id)
        }
    }
}