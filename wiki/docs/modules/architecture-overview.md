# Architectural Overview

![arch_diagram.png](./assets/arch-diagram.png)


## Data Flow

Let’s start explaining Data Flow in the Architecture as follows,

1. UI (Activity/Fragment) calls method from `ViewModel`.
2. `ViewModel` executes request with the help of [Request Manager](request-manager.md).
3. The request is further wrapped within a [exception handler](exception-handling.md) to manage automatic exception handling.
4. The request combines data from multiple repositories.
5. Each Repository can return data from multiple **Data Source** (Cached, Remote or more).
6. Information flows back to the UI where we display the list of requested information.



## Module Types and Dependencies

The application adopts modularized code-base approach which provides few benefits like,

* Better [separation of concerns](https://en.wikipedia.org/wiki/Separation_of_concerns). Each module has a clear API., Feature related classes live in different modules and can't be referenced without explicit module dependency.
* Faster compile time



