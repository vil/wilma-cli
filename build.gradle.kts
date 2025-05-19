import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.1.21"
    application
}

group = "dev.vili"
version = "0.1.1"

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

    implementation("org.openwilma:kotlin:0.9.21-BETA")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    implementation("com.google.code.gson:gson:2.13.1")

}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "21"
}

tasks.withType<Jar> {
    // Otherwise you'll get a "No main manifest attribute" error
    manifest {
        attributes["Main-Class"] = "dev.vili.wilmacli.WilmaCLI"
    }

    // To avoid the duplicate handling strategy error
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    // To add all the dependencies
    from(sourceSets.main.get().output)

    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}


application {
    mainClass.set("dev.vili.wilmacli.WilmaCLI")
}