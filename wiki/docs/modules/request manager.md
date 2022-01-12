# Request Manager

Request manager is used to process any request from Viewmodel to respective Repositories.



## Usability

1. Wrap the request inside the RequestManager object like below,here T can be class of interest for example User in this case

   ```
   object : RequestManager<T>(preCheck = {
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

3. **createCall** :- Any request which is suspend function can be executed here.

4. **Resource**:- Result will be wrapped inside [Resource](https://github.com/NeoSOFT-Technologies/mobile-android/blob/main/domain/src/main/java/com/core/utils/Resource.kt) where below status are present, 

   - Resource.LOADING

   - Resource.SUCCESS

   - Resource.ERROR

3. To handle the exception for the each request we need to wrap the above RequestManager call in ExceptionHandler to manage the exception automatically.Request is handled via exception handler and the exception is received in the catch block.

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

6. If you wish to handle the exception to make some custom behaviour to your UI you can return **true** else return **false** when u want the automatic exception handler behaviour defined in the viewmodel.

  	 To learn more about architecture see [exception handler](exception-handling.md)

