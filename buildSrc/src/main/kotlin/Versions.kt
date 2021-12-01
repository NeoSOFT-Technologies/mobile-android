// Version constants for the Kotlin DSL dependencies
// This file helps us separate our versioning of the libraries in one place.
object Versions {
    //app level
    const val gradle = "7.0.3"
    const val kotlin = "1.6.0"
    const val jacoco = "0.8.6"
    const val sonarqube = "2.7.1"

    //libs
    val coreKtx = "1.7.0"
    val appcompat = "1.4.0"
    val constraintLayout = "2.1.2"
    val materialApp = "1.4.0"

    //test
    val junit = "4.13.2"
    val extJunit = "1.1.3"
    val espresso = "3.4.0"
}