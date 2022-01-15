package com.arch.data.source.user.remote

import com.arch.data.TestDataGenerator
import com.arch.data.network.RetrofitAppServices
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserRemoteDataSourceImplTest {

    @Mock
    lateinit var retrofitAppServices: RetrofitAppServices

    private lateinit var userRemoteDataSource: UserRemoteDataSource

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        userRemoteDataSource = UserRemoteDataSourceImpl(retrofitAppServices)
    }

    @Test
    fun `check remote login call`() {
        //given
        val loginRequestEntity = TestDataGenerator.getLoginRequestEntity()

        runBlocking {
            `when`(retrofitAppServices.login(loginRequestEntity)).thenReturn(
                TestDataGenerator.getLoginResponse(),
            )

            userRemoteDataSource.loginUser(loginRequestEntity).body()

            verify(retrofitAppServices, times(1)).login(loginRequestEntity)
        }
    }
}