plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.psychebytes"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.psychebytes"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // ViewPager
    implementation ("androidx.viewpager2:viewpager2:1.0.0")
    // Image Slider
    implementation ("com.github.denzcoskun:ImageSlideshow:0.1.2")
    // Gson
    implementation ("com.google.code.gson:gson:2.8.8")
    // Glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")


}