import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.21"
    application
}

group = "dev.vili"
version = "0.1.0"

repositories {
    mavenCentral()
    maven {
        name = "TestausServeri"
        url = uri("https://maven.testausserveri.fi/openwilma")
    }

    flatDir {
        dirs("libs")
    }
}

dependencies {
    testImplementation(kotlin("test"))

    implementation("org.openwilma:kotlin:0.9.18-BETA")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation("com.google.code.gson:gson:2.10.1")

}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

application {
    mainClass.set("dev.vili.wilmacli.WilmaCLI")
}