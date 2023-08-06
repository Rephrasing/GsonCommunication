plugins {
    `maven-publish`
    `java-library`
    id("java")
    id("io.freefair.lombok") version "8.1.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "io.github.rephrasing"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/com.google.code.gson/gson
    implementation("com.google.code.gson:gson:2.10.1")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar").configure {

    minimize()
    archiveFileName.set("${project.name}-v${project.version}.jar")
    destinationDirectory.set(file("$rootDir/output"))
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}
