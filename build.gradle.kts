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
