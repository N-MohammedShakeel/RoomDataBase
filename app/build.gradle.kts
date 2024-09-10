plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "com.example.roomdatabase"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.roomdatabase"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"  // Or "11" if you need newer features
    }

    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.support.annotations)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    kapt("androidx.room:room-compiler:2.6.1") // Use matching version
    kapt("androidx.lifecycle:lifecycle-compiler:2.8.5")

    // Room Database
    implementation("androidx.room:room-runtime:2.6.1") // Use matching version
    implementation("androidx.room:room-ktx:2.6.1") // Use matching version

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.5")

    // LiveData (commonly used with ViewModel)
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.5")

    // Lifecycle components
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.5")

    // Annotation support
    implementation("androidx.annotation:annotation:1.8.2")
}
