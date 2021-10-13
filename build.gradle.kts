plugins {
    application
    kotlin("jvm") version "1.5.31"
    id("org.jetbrains.dokka") version "1.5.30"
}

application {
    mainClass.set("vision.kodai.cbc.MainKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.michael-bull.kotlin-result:kotlin-result:1.1.12")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
}
