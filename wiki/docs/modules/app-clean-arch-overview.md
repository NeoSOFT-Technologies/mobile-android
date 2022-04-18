# CLEAN Architecture Overview



## CLEAN Architecture by Robert C. Martin

![app-clean-architecture.drawio.svg](./assets/clean/app-clean-architecture.drawio.svg)



## Onion Layers CLEAN Architecture

![clean-architecture-onion-rings.svg](./assets/clean/clean-architecture-onion-rings.svg)



## CLEAN Architecture in Android App

Let’s start explaining Data Flow in the Architecture as follows,

1. UI (Activity/Fragment) calls method from `ViewModel`.
2. `ViewModel` executes request(use-case) with the help of [Request Manager](request-manager.md).
3. The request is further wrapped within a [exception handler](exception-handling.md) to manage automatic exception handling.
4. The request combines data from multiple repositories.
5. Each Repository can return data from multiple **Data Source** (Cached, Remote or more).
6. Information flows back to the UI where we display the list of requested information.

![clean-android-app.svg](./assets/clean/clean-android-app.svg)



## Implementing CLEAN Architecture in Android App

![implementating-clean-android-app.svg](./assets/clean/implementating-clean-android-app.svg)







