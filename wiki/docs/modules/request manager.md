# Request Manager

Request manager is used to process any request from viewmodel to respective repositories.



## Usability

1. Wrap the request inside the RequestManager object like below,

```
object : RequestManager<User>(preCheck = {
    when {
        Validator.isEmpty(email) -> throw AppError(
            appErrorType = AppErrorType.EmailEmpty,
        )
        !Validator.isValidEmail(email) -> throw AppError(
            appErrorType = AppErrorType.InvalidEmail,
        )
        Validator.isEmpty(password) -> throw AppError(
            appErrorType = AppErrorType.PasswordEmpty,
        )
    }
}) {
    override suspend fun createCall(): Either<BaseError, User> {
        return userRepository.login(email, password)
    }
}.asFlow().collect {
    _tokenFlow.value = it
}
```

2. **precheck** : It will check all the presentation validation like checking if email is present and is it valid or not.

3. **createCall** :- Where repository method will be executed and result will be get in collect.

4. **Resource**:- Result will be wrapped inside [Resource](https://github.com/NeoSOFT-Technologies/mobile-android/blob/main/domain/src/main/java/com/core/utils/Resource.kt) where below status are present, 

   	1. Resource.LOADING
   	1. Resource.SUCCESS
   	1. Resource.ERROR

5. To handle the exception for the each request we need to wrap the above RequestManager call in ExceptionHandler to manage the exception automatically.Request is handled via exception handler and the exception is received in the catch block.

   ```
   exceptionHandler.handle {
       object : RequestManager<User>(preCheck = {
           when {
               Validator.isEmpty(email) -> throw AppError(
                   appErrorType = AppErrorType.EmailEmpty,
               )
               !Validator.isValidEmail(email) -> throw AppError(
                   appErrorType = AppErrorType.InvalidEmail,
               )
               Validator.isEmpty(password) -> throw AppError(
                   appErrorType = AppErrorType.PasswordEmpty,
               )
           }
       }) {
           override suspend fun createCall(): Either<BaseError, User> {
               return userRepository.login(email, password)
           }
       }.asFlow().collect {
           _tokenFlow.value = it
       }
   }.catch<Exception> {
       false
   }.execute()
   ```

6. To manage the exception its important to return **true** from the catch block to handle the exception and **false** to handle by system.

To learn more about architecture see [exception handler](exception-handling.md)

