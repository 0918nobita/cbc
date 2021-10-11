plugins {
    application
    kotlin("jvm") version "1.5.31"
}

application {
    mainClass.set("vision.kodai.cbc.MainKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.michael-bull.kotlin-result:kotlin-result:1.1.12")
}
