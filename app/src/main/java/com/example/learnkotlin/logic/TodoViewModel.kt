package com.example.learnkotlin.logic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.learnkotlin.MainApplication
import com.example.learnkotlin.api.Prefs
import com.example.learnkotlin.data.Todo
import com.example.learnkotlin.data.TodoRepository
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {
//    private val _todoList = MutableLiveData<List<Todo>>()
//    val todoList: LiveData<List<Todo>> = _todoList


    private val repository: TodoRepository
    val todoList: LiveData<List<Todo>>

    init {
        val todoDao = MainApplication.todoDatabase.getTodoDao()

        repository = TodoRepository(todoDao)
        todoList = repository.allTodos

        if (Prefs.isFirstRun(application)) {
            viewModelScope.launch {
                repository.refreshTodos()
                Prefs.setFirstRunDone(application)
            }
        }
    }

//    fun getAllTodo() {
//        _todoList.value = TodoManager.getAllTodo().sortedByDescending { it.createAt }
//    }

    fun addTodo(title: String) {
//        TodoManager.addTodo(title)
//        getAllTodo()

        viewModelScope.launch {
            repository.addTodo(title)
        }
    }

    fun deleteTodo(id: Int) {
//        TodoManager.deleteTodo(Id)
//        getAllTodo()

        viewModelScope.launch {
            repository.deleteTodo(id)
        }
    }

    fun reorder(from: Int, to: Int) {
        viewModelScope.launch {
            repository.reorderTodos(from, to)
        }
    }
}