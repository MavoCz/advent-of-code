import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.0"
    application
}

group = "net.voldrich.adventofcode"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.github.kittinunf.fuel:fuel:2.3.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.0")
    implementation("org.jheaps:jheaps:0.14")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "17"
}

application {
    mainClass.set("MainKt")
}
