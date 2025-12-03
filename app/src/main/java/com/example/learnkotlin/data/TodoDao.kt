package com.example.learnkotlin.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TodoDao {

    @Query("SELECT * FROM Todo ORDER BY orderIndex ASC")
    fun getAllTodo(): LiveData<List<Todo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(todos: List<Todo>)

    @Insert
    suspend fun addTodo(todo: Todo)

    @Query("DELETE FROM Todo WHERE id = :Id")
    suspend fun deleteTodo(Id: Int)

    @Query("SELECT * FROM Todo ORDER BY orderIndex ASC")
    suspend fun getAllTodoSync(): List<Todo>

    @Query("DELETE FROM Todo")
    suspend fun deleteAll()
}
