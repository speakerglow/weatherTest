package com.example.weathertest.storage
/*
* apply plugin: 'com.android.application'
apply plugin: 'kotlin-android'
apply plugin: 'kotlin-android-extensions'
apply plugin: 'kotlin-kapt'
apply plugin: "androidx.navigation.safeargs.kotlin"
apply plugin: "dagger.hilt.android.plugin"

android {
    compileSdkVersion 31
    buildToolsVersion "30.0.3"

    defaultConfig {
        configurations.all {
            resolutionStrategy { force 'androidx.core:core-ktx:1.6.0' }
        }
        applicationId "com.example.weathertest"
        minSdkVersion 23
        targetSdkVersion 31
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            minifyEnabled false
            useProguard false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
    buildFeatures {
        viewBinding true
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = '1.8'
    }
    androidExtensions {
        experimental = true
    }
}

dependencies {

    implementation "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
    implementation 'androidx.core:core-ktx:1.7.0'
    implementation 'androidx.appcompat:appcompat:1.4.1'
    implementation 'com.google.android.material:material:1.5.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.3'
    testImplementation 'junit:junit:4.+'
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'

    def lifecycleVersion = '2.2.0'

    //LiveData
    implementation "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"
    implementation "androidx.lifecycle:lifecycle-common-java8:$lifecycleVersion"
    implementation "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"

    //ViewModel
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
    implementation "androidx.fragment:fragment-ktx:1.3.6"

    //Navigation
    implementation "androidx.navigation:navigation-fragment-ktx:2.3.5"
    implementation 'androidx.navigation:navigation-ui-ktx:2.3.5'

    //Dagger
    implementation "com.google.dagger:hilt-android:$hilt_version"
    kapt "com.google.dagger:hilt-android-compiler:$hilt_version"

    //Retrofit
    def retrofitVersion = '2.9.0'
    implementation "com.squareup.retrofit2:retrofit:$retrofitVersion"
    implementation "com.squareup.retrofit2:converter-moshi:$retrofitVersion"

    //Coroutines
    def coroutinesVersion = '1.3.9'
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"

    //RecyclerView
    implementation 'androidx.recyclerview:recyclerview:1.2.1'
    implementation 'com.hannesdorfmann:adapterdelegates4:4.3.0'

    //View binding
    implementation 'com.github.kirich1409:viewbindingpropertydelegate-noreflection:1.4.4'

    //Okhttp
    implementation "com.squareup.okhttp3:logging-interceptor:4.8.0"
}
* */