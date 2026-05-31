
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.plugin.parcelize")
    kotlin("plugin.serialization")
//    `maven-publish`
    id("maven-publish")
}

kotlin { jvmToolchain(21) }

android {
    namespace = "ac.mdiq.podcinilib"

    compileSdk = 36
    buildToolsVersion = "36.1.0"

    defaultConfig {
        minSdk = 26
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        aidl = true
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

buildscript {
    val kotlinVersion by extra("2.3.20")
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.18.0")
    implementation("androidx.compose.runtime:runtime:1.7.8")

    implementation("io.ktor:ktor-http:3.5.0")
    implementation("io.ktor:ktor-client-core:3.5.0")
    implementation("io.ktor:ktor-client-okhttp:3.5.0")
    implementation("io.ktor:ktor-client-cio:3.5.0")
    implementation("io.ktor:ktor-utils:3.5.0")

}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.github.xilinjia"
            artifactId = "PodciniLib"
            version = "1.0.3"
            afterEvaluate {
                from(components["release"])
            }
        }
    }
}
