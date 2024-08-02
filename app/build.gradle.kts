plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.golanesia.myapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.golanesia.sholawatburdah"
        minSdk = 22
        targetSdk = 34
        versionCode = 2
        versionName = "1.0.2"

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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation("com.github.jeancsanchez:JcPlayer:2.7.2")
    implementation("com.google.android.gms:play-services-ads:23.2.0")
}