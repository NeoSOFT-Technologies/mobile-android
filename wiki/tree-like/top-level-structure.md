## Top-level directory layout

app                          # Application core implementation
  .github/workflows        # Github workflow files
  buildSrc                 # Kotlin DSL for setup build & dependencies
  crash-reporting          # Crash reporting module/lib
  biometric                # Biometric Authenticator
  data                     # Layer exposes all data source
  domain                   # Domain layer contains all the use cases of your application
  presentation             # Prepares data for the application layer & maintains state
  errors                   # Exception handling pure Kotlin implementation
  errors-android           # Exception handling android counterpart implementation
  geolocation              # Module to track user geolocation
  media-android            # Media Picker pure Kotlin implementation
  media-library            # Media Picker android counterpart implementation
  permissions              # Permission handling pure Kotlin implementation
  permissions-android      # Permission handling android counterpart implementation
  logger                   # Text-based logging library
  screenshots              # Project screenshots
  utils                    # Project utils
  wiki                     # Documentation files

