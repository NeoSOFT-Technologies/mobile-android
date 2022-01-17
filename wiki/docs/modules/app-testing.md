# Fundamentals of testing Android apps

This page outlines the core tenets of testing Android apps, including the central best practices and their benefits.

## Benefits of testing

Testing is an integral part of the app development process. By running tests against your app consistently, you can verify your app's correctness, functional behavior, and usability before you release it publicly.

You can *manually* test your app by navigating through it. You might use different devices and emulators, change the system language, and try to generate every user error or traverse every user flow.

However, manual testing scales poorly, and it can be easy to overlook regressions in your app's behavior. *Automated testing* involves using tools that perform tests for you, which is faster, more repeatable, and generally gives you more actionable feedback about your app earlier in the development process.



Tests also vary depending on *size*, or *degree of isolation*:

- **Unit tests** or **small tests** only verify a very small portion of the app, such as a method or class.
- **End-to-end** tests or **big tests** verify larger parts of the app at the same time, such as a whole screen or user flow.
- **Medium tests** are in between and check the **integration** between two or more units.

![Tests can be either small, medium, or big.](https://developer.android.com/training/testing/fundamentals/test-scopes.png)





## What to test in Android

What you should test depends on factors such as the type of app, the development team, the amount of legacy code, and the architecture used.

#### Organization of test directories

A typical project in Android Studio contains two directories that hold tests depending on their execution environment. Organize your tests in the following directories as described:

- The `androidTest` directory should contain the tests that run on real or virtual devices. Such tests include integration tests, end-to-end tests, and other tests where the JVM alone cannot validate your app's functionality.
- The `test`directory should contain the tests that run on your local machine, such as unit tests. In contrast to the above, these can be tests that run on a local JVM.
  

## Essential unit tests

When following best practice, you should ensure you use unit tests in the following cases:

- **Unit tests** for **ViewModels**, or presenters.
- **Unit tests** for the **data** layer, especially repositories. Most of the data layer should be platform-independent. Doing so enables test doubles to replace database modules and remote data sources in tests. See the guide on [using test doubles in Android](https://developer.android.com/training/testing/fundamentals/test-doubles)
- **Unit tests** for other platform-independent layers such as the **Domain** layer, as with use cases and interactors.
- **Unit tests** for **utility classes** such as string manipulation and math.



### Testing Edge Cases

Unit tests should focus on both normal and edge cases. Edge cases are uncommon scenarios that human testers and larger tests are unlikely to catch. Examples include the following:

- Math operations using negative numbers, zero, and [boundary conditions](https://en.wikipedia.org/wiki/Off-by-one_error).
- All the possible network connection errors.
- Corrupted data, such as malformed JSON.
- Simulating full storage when saving to a file.
- Object recreated in the middle of a process (such as an activity when the device is rotated).

In this app we have done both unit testing as well as AndroidInstrumentTesting.



### Unit Tests to Avoid

Some unit tests should be avoided because of their low value:

- Tests that verify the correct operation of the framework or a library, not your code.
- Framework entry points such as *activities, fragments, or services* should not have business logic so unit testing shouldn't be a priority. Unit tests for activities have little value, because they would cover mostly framework code and they require a more involved setup. Instrumented tests such as UI tests can cover these classes.



## UI tests

There are several types of UI tests you should employ:

- **Screen UI tests** check critical user interactions in a single screen. They perform actions such as clicking on buttons, typing in forms, and checking visible states. One test class per screen is a good starting point.
- **User flow tests** or **Navigation tests**, covering most common paths. These tests simulate a user moving through a navigation flow. They are simple tests, useful for checking for run-time crashes in initialization.

# Given When Then — Our Testing Approach

Given When Then (GWT) is an approach adopted by many teams. It’s a style of writing tests which lays out a framework for what a test should look like.

**Given** — This is where you define the state of your application up to this point. You provide any data or context. Usually, this involves setting up mocks or stubbing methods.

**When** — This is the part of your application you are testing. Usually, this is a method call.

**Then** — This is where you verify a particular behaviour or outcome. What did you expect to happen?


For Example, 

### Repository Unit Test

We have written test cases for repository remote layer. You can find below snippet for repository unit test,

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

As you can see for given set of input data we have asserted the values as well as verify wether the required method calls were maid or not.

To learn more about this [`UserRepositoryTest`](https://github.com/NeoSOFT-Technologies/mobile-android/blob/main/data/src/test/java/com/arch/data/repository/UserRepositoryImplTest.kt) 



### Database Testing

We have written test case for room database operation wether the data insertion is done successfully or not. You can find it below,

```
@Test
    fun writeUser() {
        runBlocking {
            val index = userEntityDao.insert(UserEntity("test@gmail.com", "1234243242"))
            assertEquals(1, index)
        }
    }
```

To learn more about this [`AppDatabaseTest`](https://github.com/NeoSOFT-Technologies/mobile-android/blob/main/data/src/androidTest/java/com/arch/data/database/AppDatabaseTest.kt) 



### Remote Testing

We have written test case for retrofit network operation wether the api accessible or no. You can find it below,





### Android Instrumentation Testing

For instrumentation testing we have used [`espresso`](https://developer.android.com/training/testing/espresso) to record our test. We also have checked wether validation for the login page are proper and once verified it successfully logged in the user to dashboard screen.



You can further write more test cases as per the each layer & module.

