package com.example.todoapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Returns the instance of the TODO database.
 * If the database instance already exists, it returns the existing instance.
 * If not, it creates a new database instance and returns it.
 * @param context The context of the application.
 * @return The instance of the TODO database.
 */

@Database(entities = [TODO_Entity::class], version = 1, exportSchema = false)
abstract class TODO_Database : RoomDatabase() {
    abstract fun getTODO_Dao(): TODO_Dao

    companion object {
        @Volatile
        private var INSTANCE: TODO_Database? = null

        fun getDatabase(context: Context): TODO_Database {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TODO_Database::class.java,
                    "todo_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
