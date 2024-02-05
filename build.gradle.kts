plugins {
    kotlin("jvm") version "1.9.22"
}

group = "kr.doka.lab"
version = "1.0-SNAPSHOT"

repositories {
    //mavenCentral()
    //maven("https://jitpack.io")
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation("org.jsoup:jsoup:1.17.2")
    implementation("com.jayway.jsonpath:json-path:2.9.0")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}