package com.example.learnkotlin.api

import android.content.Context
import androidx.core.content.edit

// Save status if first run
object Prefs {
    private const val KEY_FIRST_RUN = "first_run"

    fun isFirstRun(context: Context): Boolean {
        val prefs = context.getSharedPreferences("todo_prefs", Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_FIRST_RUN, true)
    }

    fun setFirstRunDone(context: Context) {
        val prefs = context.getSharedPreferences("todo_prefs", Context.MODE_PRIVATE)
        prefs.edit { putBoolean(KEY_FIRST_RUN, false) }
    }
}