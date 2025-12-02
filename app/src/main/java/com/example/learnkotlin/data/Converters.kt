package com.example.learnkotlin.data

import androidx.room.TypeConverter
import java.util.Date

// Convert time
class Converters {

    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun toDate(time: Long): Date {
        return Date(time)
    }
}