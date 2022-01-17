# App Testing

In this app we have done both unit testing as well as AndroidInstrumentTesting.

### Unit Testing

We have written test cases for repository,remote layer.you can find below snippet for repository unit test,

```
        //given
        val email = TestDataGenerator.getEmail()
        val password = TestDataGenerator.getPassword()
        val loginRequestEntity=TestDataGenerator.getLoginRequestEntity()
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
```

As you can see for given set of input data we have assert the value as well as verify wether the required method calls were maid or not.

To learn more about this [`UserRepositoryTest`](https://developer.android.com/topic/libraries/data-binding/) 

### Database Testing

We have written test case for room db operation wether the data insertion is done successfully or not.You can find it below,

```
@Test
    fun writeUser() {
        runBlocking {
            val index = userEntityDao.insert(UserEntity("test@gmail.com", "1234243242"))
            assertEquals(1, index)
        }
    }
```

To learn more about this [`AppDatabaseTest`](https://developer.android.com/topic/libraries/data-binding/) 

### Android Instrumentation Testing

For instrumentation testing we have used [`espresso`](https://developer.android.com/training/testing/espresso) to record our test.we have checked weather validation for the login page are proper and once verified it successfully logged in the user to dashboard screen.

