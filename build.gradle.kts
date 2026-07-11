
plugins {
    id("com.android.library") version "9.2.1"
    id("org.jetbrains.kotlin.plugin.parcelize") version "2.4.0"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.4.0"
    id("maven-publish")
}

kotlin { jvmToolchain(21) }

android {
    namespace = "ac.mdiq.podcinilib"

    compileSdk = 37
    buildToolsVersion = "37.0.0"

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
    val kotlinVersion by extra("2.4.0")
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.19.0")
    implementation("androidx.compose.runtime:runtime:1.11.4")

    implementation("io.ktor:ktor-http:3.5.1")
    implementation("io.ktor:ktor-client-core:3.5.1")
    implementation("io.ktor:ktor-client-okhttp:3.5.1")
    implementation("io.ktor:ktor-client-cio:3.5.1")
    implementation("io.ktor:ktor-utils:3.5.1")

}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.github.xilinjia"
            artifactId = "PodciniLib"
            version = "1.0.9"
            afterEvaluate {
                from(components["release"])
            }
        }
    }
}
