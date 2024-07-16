plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "im.niu.corelib"
    compileSdk = 34

    defaultConfig {
        minSdk = 28

        consumerProguardFiles("consumer-rules.pro")
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

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("com.tencent:mmkv:1.3.7")
    implementation("org.java-websocket:Java-WebSocket:1.5.6")
    implementation("com.google.protobuf:protobuf-java:4.27.2")
    implementation("org.greenrobot:eventbus:3.3.1")
    implementation("androidx.work:work-runtime:2.9.0")
    implementation("com.github.getActivity:XXPermissions:18.63")
    implementation("com.google.code.gson:gson:2.11.0")
}