plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    namespace = "com.niu.protect.lib"
    compileSdk = 34

    defaultConfig {
        minSdk = 28

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.8.22")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")

    implementation("org.java-websocket:Java-WebSocket:1.5.6")
    implementation("com.google.code.gson:gson:2.8.9")

    implementation("org.greenrobot:eventbus:3.3.1")
    implementation("androidx.work:work-runtime:2.9.0")

//    implementation(fileTree(dir: "libs", include: ["*.jar"]))
    implementation("com.github.crazyandcoder:citypicker:6.0.2")

    implementation("com.soundcloud.android:android-crop:1.0.1@aar")
    implementation("com.tencent.map.geolocation:TencentLocationSdk-openplatform:7.5.4.3")
//    implementation("io.github.xiaofeidev:round:1.1.2"
//    implementation("com.hjq:xxpermissions:9.0"
    implementation("com.github.getActivity:XXPermissions:18.63")
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("cn.bertsir.zbarLibary:zbarlibary:1.4.2")
    implementation("com.zyao89:zloading:1.2.0")
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation( "org.greenrobot:eventbus:3.3.1")
    implementation("com.tencent:mmkv:1.3.5")

    implementation("cn.bingoogolapple:bga-qrcodecore:1.1.9")
//    implementation("cn.bingoogolapple:bga-zxing:+"
//    implementation("cn.bingoogolapple:bga-photopicker:2.0.2"
//    implementation("cn.bingoogolapple:bga-baseadapter:2.0.0@aar"
    implementation("com.contrarywind:Android-PickerView:4.1.9")
    implementation("com.github.crazyandcoder:citypicker:6.0.2")
//
    implementation("com.github.gzu-liyujiang.AndroidPicker:Common:4.1.13")
    implementation("com.github.gzu-liyujiang.AndroidPicker:WheelPicker:4.1.13")
    implementation("com.github.bumptech.glide:glide:4.16.0")

}