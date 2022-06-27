package com.arch.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arch.data.database.dao.ResourceEntityDao
import com.arch.data.database.dao.UserEntityDao
import com.arch.data.entity.local.ResourceEntity
import com.arch.data.entity.local.UserEntity

@Database(
    entities = [
        UserEntity::class,
        ResourceEntity::class
    ],
    version = DatabaseProperties.DATABASE_VERSION,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserEntityDao
    abstract fun resourceDao(): ResourceEntityDao
}