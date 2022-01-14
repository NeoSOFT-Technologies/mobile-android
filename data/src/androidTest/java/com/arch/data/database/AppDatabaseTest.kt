package com.arch.data.database

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.arch.data.database.dao.UserEntityDao
import com.arch.data.entity.local.UserEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {
    lateinit var appDatabase: AppDatabase

    lateinit var userEntityDao: UserEntityDao

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        appDatabase =
            Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        userEntityDao = appDatabase.userDao()
    }

    @Test
    fun writeUser() {
        runBlocking {
            val index = userEntityDao.insert(UserEntity("test@gmail.com", "1234243242"))
            assertEquals(1, index)
        }
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }
}