plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.gsrocks.locationmaps.core.geocoding"
    compileSdk = 33

    defaultConfig {
        minSdk = 21
    }

    buildFeatures {
        aidl = false
        buildConfig = false
        renderScript = false
        shaders = false
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":core-model"))
    implementation(project(":core-common"))

    implementation(libs.androidx.core.ktx)

    // Arch Components
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
