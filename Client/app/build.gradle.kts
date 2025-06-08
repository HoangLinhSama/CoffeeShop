import java.util.regex.Pattern.compile

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")

    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")
}

android {
    namespace = "com.hoanglinhsama.client"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.hoanglinhsama.client"
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
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.6")
    implementation("androidx.activity:activity-compose:1.9.2")
    implementation(platform("androidx.compose:compose-bom:2024.09.03"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.09.03"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Splash Api
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Compose Navigation
    implementation("androidx.navigation:navigation-compose:2.8.2")

    // Dagger Hilt
    implementation("com.google.dagger:hilt-android:2.49")
    kapt("com.google.dagger:hilt-android-compiler:2.45")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Retrofit 2
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.5.0")

    // Coil
    implementation("io.coil-kt:coil-compose:2.4.0")

    // Datastore
    implementation("androidx.datastore:datastore-preferences:1.1.1")
    implementation("androidx.datastore:datastore-preferences-core:1.1.1")

    // Paging 3
    implementation("androidx.paging:paging-runtime-ktx:3.3.2")
    implementation("androidx.paging:paging-compose:3.3.2")

    // Room
    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    // Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:2.6.1")

    // Constraint Layout Jetpack Compose
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    //Compose Foundation
    implementation("androidx.compose.foundation:foundation:1.7.3")

    //Accompanist
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.31.4-beta")

    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))

    // Add the dependency for the Firebase Authentication library
    implementation("com.google.firebase:firebase-auth")

    //  Swipe Box
    implementation("io.github.kevinnzou:compose-swipebox:1.4.0")

    //  ZXing
    implementation("com.google.zxing:core:3.5.2")
    implementation("androidx.core:core-ktx:1.12.0")

    // ZaloPay
    implementation(
        fileTree(
            mapOf<String, Any>(
                "dir" to "D:\\xampp\\htdocs\\CoffeeShop\\Client\\app\\libs",
                "include" to listOf("*.aar", "*.jar"),
                "exclude" to emptyList<String>()
            )
        )
    )

    // Testing

    // Mockito
    testImplementation("org.mockito:mockito-core:5.2.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")
    testImplementation("org.mockito:mockito-inline:5.2.0")

    // Kotlin coroutine test
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    // Paging test
    testImplementation("androidx.paging:paging-common-ktx:3.3.2")

    // Turbine
    testImplementation("app.cash.turbine:turbine:0.13.0")

    // Google Truth
    testImplementation ("com.google.truth:truth:1.4.0")

    // AndroidX Test Core Library
    testImplementation ("androidx.test:core:1.5.0")

    // Robolectric
    testImplementation("org.robolectric:robolectric:4.10.3")

    // AndroidX Paging - Common
    testImplementation("androidx.paging:paging-testing:3.3.2")

    // Core-Testing
    testImplementation ("androidx.arch.core:core-testing:2.2.0")
}