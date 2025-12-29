plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.animeseriesapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.animeseriesapp"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures{
        viewBinding=true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // Room Database Dependencies
    implementation(libs.androidx.room.runtime)  // Core Room library
    implementation(libs.androidx.room.ktx)       // Kotlin Extensions for coroutines support
    ksp(libs.androidx.room.compiler)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.github.bumptech.glide:glide:4.11.0")
        // Lifecycle ViewModel KTX (provides viewModelScope)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.3")

    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.2.0")
        // Lifecycle LiveData KTX (provides asLiveData())
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.3")

        // ... your other dependencies like room, retrofit, etc.

}