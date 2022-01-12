# Request manager

Request manager is used to process the request from viewmodel to respective repositories.



## Usability

1.First step is to define a function in viewmodel to call the respective    repository method
2.In order to call the repository method we need to wrap the request inside the RequestManager object like below

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

3. As you can see in the step2 that request manager has precheck function as an argument it will check all the presentation validation like checking weather email is present and is it valid or not.

4. Once step 3 is complete it will call the createCall function where our actual repository method will be executed and result will be get in collect.

5. In step 4 we get the result wrapped in Resource object where we will get the status along with the corresponding data,like status SUCCESS and the Data containing the result from api/database.Likewise we have other status for ERROR, LOADING as well.

6. To handle the exception for the each request we need to wrap the above RequestManager call in ExceptionHandler to manage the exception automatically.

7. To understand the step 6 refere below 

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

​    		As seen above the request is handled via exception handler and the     			exception is received in the catch block.

8. If you want to manage the exception at this point its important to return **true** from the catch block that way its understand to the system that this particular exception is already handled,otherwise return **false** indicating the exception is handled by system.

for more info please refere [exception handler](exception-handling.md)

