package com.arch.data.database

import android.content.Context
import androidx.room.Room

object DatabaseProperties {
    private const val DATABASE_NAME = "com_arch_blueprint_app"
    const val DATABASE_VERSION = 1

    @Volatile
    private var instance: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
        return instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also { instance = it }
        }
    }

    private fun buildDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
}