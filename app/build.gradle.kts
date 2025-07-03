plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.tjlabs.tjlabsvenus_sdk_android"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.tjlabs.tjlabsvenus_sdk_android"
        minSdk = 29
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
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation ("com.tjlabs:appcommonlib:1.0.2-olympus-platform")
    implementation ("com.tjlabs:appresourcelib:1.0.2")

    implementation ("com.tjlabs:appauthlib:1.0.2")
    implementation ("androidx.security:security-crypto-ktx:1.1.0-alpha03") //auth 사용을 위해 같이 추가해야함

    implementation ("com.google.android.material:material:1.9.0")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}