plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.secrets)
    alias(libs.plugins.kapt)
    alias(libs.plugins.kotlinSerialization)
}

android {
    namespace = "nl.mranderson.rijks"
    compileSdk = 34

    defaultConfig {
        applicationId = "nl.mranderson.rijks"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
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
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
}

secrets {
    propertiesFileName = "secrets.properties"
    defaultPropertiesFileName = "local.defaults.properties"
}

dependencies {
    implementation(libs.androidx.activity)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.ktx)
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.lifecycle)
    implementation(libs.androidx.paging)
    implementation(libs.compose.foundation)
    implementation(libs.compose.material3)
    implementation(libs.compose.navigation)
    implementation(libs.compose.ui)
    implementation(libs.compose.uiTooling)
    implementation(libs.coilCompose)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation)
    implementation(libs.kotlinSerialization)
    implementation(libs.lifecycle.compose)
    implementation(libs.material)
    implementation(libs.retrofit2)
    implementation(libs.retrofitSerialization)

    // Kapt Dependency
    kapt(libs.hilt.workCompiler)

    // Chucker Dependency
    debugImplementation(libs.chucker)
    releaseImplementation(libs.chucker)

    // Test Dependencies
    testImplementation(libs.androidx.arch.core)
    testImplementation(libs.coroutinesTest)
    testImplementation(libs.jupiter)
    testImplementation(libs.jupiter.engine)
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)

    // Android Test Dependencies
    androidTestImplementation(libs.androidx.test)
    androidTestImplementation(libs.androidx.test.compose.ui)
    androidTestImplementation(libs.androidx.test.espresso)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}