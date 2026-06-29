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

}







dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:2.2.0")
    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("com.google.android.material:material:1.14.0")

    implementation("org.java-websocket:Java-WebSocket:1.6.0")
    implementation("com.google.code.gson:gson:2.14.0")

    implementation("org.greenrobot:eventbus:3.3.1")
    implementation("androidx.work:work-runtime:2.11.2")

//    implementation(fileTree(dir: "libs", include: ["*.jar"]))
    implementation("com.github.crazyandcoder:citypicker:6.0.2")

    implementation("com.soundcloud.android:android-crop:1.0.1@aar")
    implementation("com.tencent.map.geolocation:TencentLocationSdk-openplatform:8.7.5.1")
//    implementation("io.github.xiaofeidev:round:1.1.2"
//    implementation("com.hjq:xxpermissions:9.0"
    implementation("com.github.getActivity:XXPermissions:20.0")
    implementation("com.google.code.gson:gson:2.14.0")
    implementation("cn.bertsir.zbarLibary:zbarlibary:1.4.2")
    implementation("com.zyao89:zloading:1.2.0")
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation("com.tencent:mmkv:2.4.0")

    implementation("cn.bingoogolapple:bga-qrcodecore:1.1.9")
//    implementation("cn.bingoogolapple:bga-zxing:+"
//    implementation("cn.bingoogolapple:bga-photopicker:2.0.2"
//    implementation("cn.bingoogolapple:bga-baseadapter:2.0.0@aar"
    implementation("com.contrarywind:Android-PickerView:4.1.9")
//
    implementation("com.github.gzu-liyujiang.AndroidPicker:Common:4.1.13")
    implementation("com.github.gzu-liyujiang.AndroidPicker:WheelPicker:4.1.13")
    implementation("com.github.bumptech.glide:glide:5.0.7")

}