buildscript {
    val kotlinVersion = "1.6.21"

    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven ("https://s01.oss.sonatype.org/content/repositories/releases/")
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("com.android.tools.build:gradle:7.0.4")
        classpath ("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
        maven ("https://s01.oss.sonatype.org/content/repositories/releases/")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

plugins {
    id("org.jetbrains.kotlinx.kover") version "0.5.0"
}
