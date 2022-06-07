import org.gradle.api.artifacts.dsl.DependencyHandler

object AppDependencies {
    //std lib
    val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"

    //android UI
    private val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coreCoroutines}"
    private val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    private val materialApp = "com.google.android.material:material:${Versions.materialApp}"

    //Dependency Injection
    private val hiltCore = "com.google.dagger:hilt-core:${Versions.hiltVersion}"
    private val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hiltVersion}"
    private val hiltAndroidCompiler =
        "com.google.dagger:hilt-android-compiler:${Versions.hiltVersion}"
    private val hiltCoreCompiler =
        "androidx.hilt:hilt-compiler:${Versions.hiltOnlyCompilerVersion}"
    private val javaxInject = "javax.inject:javax.inject:${Versions.javaxInjectVersion}"

    // http
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
    val retrofitCoverterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofitVersion}"
    val retrofitLogginInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okhttpLoggingVersion}"

    val moshiVersion = "com.squareup.moshi:moshi:${Versions.moshVersion}"
    val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshVersion}"
    val moshiCodeGen ="com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshVersion}"

    //Architecture components
    private val lifecycleLivedataKtx =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleVersion}"
    private val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragmentKtx}"

    //test libs
    private val junit = "junit:junit:${Versions.junit}"
    private val extJUnit = "androidx.test.ext:junit:${Versions.extJunit}"
    private val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    private val mockito = "org.mockito:mockito-core:${Versions.espresso}"
    private val androidCoreTesting =
        "androidx.arch.core:core-testing:${Versions.androidCoreTesting}"

    //Room libs
    val roomRuntime = "androidx.room:room-runtime:${Versions.roomVersion}"
    val kaptRoomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"
    val roomKtx = "androidx.room:room-ktx:${Versions.roomVersion}"

    //Logging
    val timber = "com.jakewharton.timber:timber:${Versions.timberVersion}"

    //crash-reportiing
    val crashlytics = "com.google.firebase:firebase-crashlytics-ktx:${Versions.firebaseCrashlytics}"

    //ktx
    val reactiveStream =
        "androidx.lifecycle:lifecycle-reactivestreams-ktx:${Versions.reactiveStream}"

    //paging
    val pagingAndroid = "androidx.paging:paging-runtime:${Versions.paging}"
    val pagingCoreCommon = "androidx.paging:paging-common:${Versions.paging}"

    //location
    val locations = "com.google.android.gms:play-services-location:${Versions.location}"

    //biometrics
    val biometrics = "androidx.biometric:biometric:${Versions.biometrics}"

    //exifInterface
    val exifInterface = "androidx.exifinterface:exifinterface:${Versions.exifInterface}"

    //file picker
    val filePicker = "com.github.Lobynya:MaterialFilePicker:${Versions.filePicker}"

    val appLibraries = arrayListOf<String>().apply {
        add(kotlinStdLib)
        add(coreKtx)
        add(appcompat)
        add(constraintLayout)
        add(materialApp)
        add(fragmentKtx)
        add(timber)
        add(crashlytics)
        add(reactiveStream)
        add(pagingAndroid)
    }

    val androidTestLibraries = arrayListOf<String>().apply {
        add(extJUnit)
        add(espressoCore)
        add(androidCoreTesting)
    }

    val testLibraries = arrayListOf<String>().apply {
        add(junit)
        add(mockito)
    }
    val kpatLibraries = arrayListOf<String>().apply {
        add(hiltAndroidCompiler)
    }
}

//util functions for adding the different type dependencies from build.gradle file
fun DependencyHandler.kapt(list: List<String>) {
    list.forEach { dependency ->
        add("kapt", dependency)
    }
}

fun DependencyHandler.implementation(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun DependencyHandler.androidTestImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}

fun DependencyHandler.testImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("testImplementation", dependency)
    }
}