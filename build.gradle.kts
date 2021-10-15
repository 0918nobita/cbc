plugins {
    application
    kotlin("jvm") version "1.5.31"
    id("org.jetbrains.dokka") version "1.5.30"
}

application {
    mainClass.set("vision.kodai.cbc.MainKt")
}

tasks.test {
    useJUnitPlatform()
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.michael-bull.kotlin-result:kotlin-result:1.1.12")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")

    testImplementation("io.kotest:kotest-runner-junit5:4.6.3")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.2")
}
