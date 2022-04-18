## Top-level directory layout

.
└── app                          # Application or UI layer
    ├── .github/workflows        # Github workflow files
    ├── buildSrc                 # Kotlin DSL for setup build & dependencies
    ├── crash-reporting          # Crash reporting module/lib
    ├── data                     # Layer exposes all data source
    ├── domain                   # Domain layer contains all the use cases of your application
    ├── errors                   # Exception handling pure Kotlin implementation
    ├── logger                   # Text-based logging library
    ├── presentation             # Prepares data for the application layer & maintains state
    ├── screenshots              # Project screenshots
    ├── utils                    # Project utils
    └── wiki                     # Documentation files

