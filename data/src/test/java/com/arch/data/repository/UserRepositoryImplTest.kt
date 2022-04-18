package com.arch.data.repository

import com.arch.data.TestDataGenerator
import com.arch.data.entity.local.UserEntity
import com.arch.data.entity.remote.response.UserResponseEntity
import com.arch.data.source.user.local.UserLocalDataSource
import com.arch.data.source.user.remote.UserRemoteDataSource
import com.arch.error.NetworkError
import com.arch.repository.UserRepository
import com.arch.utils.Either
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class UserRepositoryImplTest {

    @Mock
    lateinit var userLocalDataSource: UserLocalDataSource

    @Mock
    lateinit var userRemoteDataSource: UserRemoteDataSource

    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        userRepository = UserRepositoryImpl(userLocalDataSource, userRemoteDataSource)
    }

    @Test
    fun `login success`() {
        //given
        val email = TestDataGenerator.getEmail()
        val password = TestDataGenerator.getPassword()
        val loginRequestEntity = TestDataGenerator.getLoginRequestEntity()
        val token = TestDataGenerator.getToken()
        val response = Response.success(UserResponseEntity(token))
        runBlocking {

            //when
            `when`(userRemoteDataSource.loginUser(loginRequestEntity)).thenReturn(response)
            when (val result = userRepository.login(email, password)) {
                is Either.Right -> {

                    //then test
                    assertEquals(token, result.right.token)
                }
            }

            //verify remote source call for api once
            verify(userRemoteDataSource, times(1)).loginUser(loginRequestEntity)
            //verify local source call for saving user info once
            verify(userLocalDataSource, times(1)).saveUserInfo(
                response.body()?.transform() ?: UserEntity()
            )
        }
    }

    @Test
    fun `login error`() {
        //given
        val email = TestDataGenerator.getEmail()
        val password = TestDataGenerator.getPassword()
        val loginRequestEntity = TestDataGenerator.getLoginRequestEntity()

        runBlocking {

            //when
            `when`(userRemoteDataSource.loginUser(loginRequestEntity)).thenReturn(
                Response.error(
                    502,
                    "{}".toResponseBody()
                )
            )
            when (val result = userRepository.login(email, password)) {
                is Either.Left -> {

                    //then test
                    assertEquals(502, (result.left as NetworkError).httpError)
                }
            }

            //verify remote source call for api once
            verify(userRemoteDataSource, times(1)).loginUser(loginRequestEntity)
            //verify no saving user info due to exception
            verify(userLocalDataSource, times(0)).saveUserInfo(UserEntity())
        }
    }
}