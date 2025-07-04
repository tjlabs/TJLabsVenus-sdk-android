plugins {
//        id("com.android.application")
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

val versionMajor = 1
val versionMinor = 0
val versionPatch = 1

android {
    namespace = "com.tjlabs.tjlabsvenus_sdk_android"
    compileSdk = 35

    defaultConfig {
//        applicationId = "com.tjlabs.tjlabsvenus_sdk_android"
//        versionCode = 1
//        versionName = "1.0"
        minSdk = 29
        targetSdk = 34

        buildConfigField("String", "TJLABS_SDK_VERSION", "\"$versionMajor.$versionMinor.$versionPatch\"")
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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation ("com.github.tjlabs:TJLabsAuth-sdk-android:1.0.2")
    implementation ("com.github.tjlabs:TJLabsCommon-sdk-android:1.0.1")
    implementation ("androidx.security:security-crypto-ktx:1.1.0-alpha03") //auth 사용을 위해 같이 추가해야함
    implementation ("com.google.android.material:material:1.9.0")

    implementation (libs.opencsv)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.androidx.core.ktx.v131)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.appcompat.v120)
    implementation(libs.material.v120)
    implementation(libs.androidx.constraintlayout.v213)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit.v113)
    androidTestImplementation(libs.androidx.espresso.core.v340)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines.android)
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                groupId = "com.github.tjlabs"
                artifactId = "TJLabsVenus-sdk-android"
                version = "$versionMajor.$versionMinor.$versionPatch"
            }
        }
    }
}
