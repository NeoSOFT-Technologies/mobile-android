# Architectural Overview

![arch_diagram.png](https://github.com/NeoSOFT-Technologies/mobile-android/blob/6ad55707fa1b84f743aaa6288608d401e4e75f1f/wiki/assets/arch_diagram.png)


### Data Flow

Let’s start explaining Data Flow in the Architecture as follows,

1. UI (Activity/Fragment) calls method from ViewModel.
2. ViewModel executes request with the help of request manager.
3. The request is further wrapped within a exception handler to manage automatic exception handling
4. The request combines data from multiple repositories.
5. Each Repository can return data from multiple Data Source (Cached, Remote or more).
6. Information flows back to the UI where we display the list of requested information.