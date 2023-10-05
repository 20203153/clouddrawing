plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("com.google.relay")
}

android {
    namespace = "kr.ac.kookmin.clouddrawing"
    compileSdk = 34

    defaultConfig {
        applicationId = "kr.ac.kookmin.clouddrawing"
        minSdk = 33
        //noinspection OldTargetApi
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
    buildFeatures {
        compose = true
        viewBinding = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }

    splits {
        abi {
            isEnable = true
            reset()
            include("x86", "armeabi-v7a", "x86_64")
            isUniversalApk = true
        }
    }
}

dependencies {
    // https://mvnrepository.com/artifact/com.google.firebase/firebase-bom
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
    implementation("com.google.firebase:firebase-analytics-ktx:21.3.0")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-auth-ktx:22.1.2")
    //noinspection GradleDependency
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.compose.material3:material3:1.1.2")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment:2.7.1")
    implementation("androidx.navigation:navigation-ui:2.7.1")
    implementation("com.google.android.gms:play-services-auth:20.7.0")

    val composeVersion = "1.4.3"
    //noinspection GradleDependency
    implementation("androidx.compose.animation:animation-core:$composeVersion")
    //noinspection GradleDependency
    implementation("androidx.compose.animation:animation:$composeVersion")
    //noinspection GradleDependency
    implementation("androidx.compose.ui:ui:$composeVersion")
    //noinspection GradleDependency
    implementation("androidx.compose.foundation:foundation:$composeVersion")
    //noinspection GradleDependency
    implementation("androidx.compose.ui:ui-geometry:$composeVersion")
    //noinspection GradleDependency
    implementation("androidx.compose.ui:ui-graphics:$composeVersion")
    //noinspection GradleDependency
    implementation("androidx.compose.foundation:foundation-layout:$composeVersion")
    //noinspection GradleDependency
    implementation("androidx.compose.runtime:runtime-livedata:$composeVersion")
    //noinspection GradleDependency
    implementation("androidx.compose.material:material:$composeVersion")
    //noinspection GradleDependency
    implementation("androidx.compose.material:material-icons-core:$composeVersion")
    //noinspection GradleDependency
    implementation("androidx.compose.material:material-icons-extended:$composeVersion")
    //noinspection GradleDependency
    implementation("androidx.compose.runtime:runtime-rxjava2:$composeVersion")
    //noinspection GradleDependency
    implementation("androidx.compose.ui:ui-text:$composeVersion")
    //noinspection GradleDependency
    implementation("androidx.compose.ui:ui-util:$composeVersion")
    //noinspection GradleDependency
    implementation("androidx.compose.ui:ui-viewbinding:$composeVersion")
    //noinspection GradleDependency
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.activity:activity-compose:1.7.2")

    //Compose Constraintlayout
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    // 카카오맵 sdk implementation 추가
    implementation("com.kakao.maps.open:android:2.6.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    //noinspection GradleDependency
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
}