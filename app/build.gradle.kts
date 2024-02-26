plugins {
    id("com.android.application")
}

android {
    namespace = "com.bilir.noteroom"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.bilir.noteroom"
        minSdk = 25
        targetSdk = 33
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
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


    // SQLite & ROOM dependencies
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    //Lifecycle ROOM deps.
    val lifecycle_version = "2.7.0"
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-extensions:2.1.0")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata:$lifecycle_version")
    //CardView
    implementation("androidx.cardview:cardview:1.0.0")
    //RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.0.0")
}