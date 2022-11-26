package com.app.fitness.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.fitness.data.model.Tracking
import com.app.fitness.data.model.Session

@Database(
    entities = [
        Session::class,
        Tracking::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun fitnessDea(): SessionDao
}