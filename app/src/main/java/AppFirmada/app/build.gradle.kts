plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.captaintask"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.captaintask"
        minSdk = 27
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner="androidx.test.runner.AndroidJUnitRunner"


        vectorDrawables.useSupportLibrary = true
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

    buildFeatures{
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
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("androidx.test.ext:junit-ktx:1.1.5")
    implementation("androidx.activity:activity:1.8.0")
    implementation("org.quanqi:android-holo-graph:0.1.0")
    implementation ("com.itextpdf:itextg:5.5.10")
    implementation ("org.apache.poi:poi:5.2.3")
    implementation ("org.apache.poi:poi-ooxml:5.2.3")



    // Dependencias de pruebas unitarias
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:3.12.4")

    // Dependencias de pruebas de Android
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test:monitor:1.6.0")
    androidTestImplementation ("androidx.test.ext:junit:latest.release")

}
