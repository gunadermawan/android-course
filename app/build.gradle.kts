plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.devtools.ksp")
    alias(libs.plugins.compose.compiler)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.gun.course"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.gun.course"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
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
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.animation.graphics.android)
    implementation(libs.ui)
    implementation(libs.androidx.animation)
    implementation(libs.androidx.material3.window.size)
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.coil.compose)
    // For AppWidgets support
    implementation(libs.androidx.glance.appwidget)
    // For interop APIs with Material 3
    implementation(libs.androidx.glance.material3)
    implementation(libs.accompanist.permissions)
    implementation(libs.maps.compose)
    implementation(libs.play.services.location)
    // room
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    // To use Kotlin Symbol Processing (KSP)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    // Paging library
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.room.paging)
    // Compose integration for Paging
    implementation(libs.androidx.paging.compose)
    implementation(libs.adapter.rxjava3)
    implementation(libs.rxjava)
    implementation(libs.rxandroid)
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    // Koin core
    implementation(libs.koin.core)
    // Koin Android
    implementation(libs.koin.android)
    // Koin for Jetpack Compose
    implementation(libs.koin.androidx.compose)
}
kapt {
    correctErrorTypes = true
}