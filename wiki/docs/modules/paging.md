# Paging Library

This page outlines the usage of library for providing pagination logic on Android.



## Modules

`paging` - contains pagination logic for kotlin android



## Features

- **Pagination** implements pagination logic for the data from abstract `PagedListDataSource`.
- Managing a data loading process using **Pagination** asynchronous functions: `loadFirstPage`, `loadNextPage`, `refresh` or their duplicates with `suspend` modifier.
- Observing states of **Pagination** using `LiveData` from android



## Usage

You can use **Pagination** in,

#### Presentation Layer

```kotlin
   class ExampleViewModel(
    exceptionHandler: IAndroidExceptionHandler,
    logger: AppLogger) : BaseViewModel(exceptionHandler, logger) {
   
     val pagination: Pagination<Int> = Pagination(
        parentScope = coroutineScope,
        dataSource = LambdaPagedListDataSource { currentList ->
            extrenalRepository.loadPage(currentList) 
        },
        comparator = Comparator { a: Int, b: Int ->
            a - b
        },
        nextPageListener = { result: Result<List<Int>> ->
            if (result.isSuccess) {
                println("Next page successful loaded")
            } else {
                println("Next page loading failed")
            }
        },
        refreshListener = { result: Result<List<Int>> ->
            if (result.isSuccess) {
                println("Refresh successful")
            } else {
                println("Refresh failed")
            }
        },
        initValue = listOf(1, 2, 3)
    )
}  
```



#### Managing Data Loading:                 

```kotlin
// Loading first page
pagination.loadFirstPage()

// Loading next page
pagination.loadNextPage()

// Refreshing pagnation
pagination.refresh()

// Setting new list
pagination.setData(itemsList)
```



#### Observing **Pagination** states:

```kotlin
// Observing the state of the pagination
pagination.state.addObserver { state: ResourceState<List<ItemClass>, Throwable> -> 
    // ...
}

// Observing the next page loading process
pagination.nextPageLoading.addObserver { isLoading: Boolean -> 
    // ...
}

// Observing the refresh process
pagination.refreshLoading.addObserver { isRefreshing: Boolean -> 
    // ...    
}
```



