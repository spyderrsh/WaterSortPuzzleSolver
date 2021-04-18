import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.30"
    id("org.jetbrains.compose") version "0.3.1"
}

group = "me.ryans"
version = "1.0"

repositories {
    jcenter()
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation("junit:junit:4.13.1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs += listOf(
            "-Xuse-experimental=kotlin.ExperimentalUnsignedTypes",
            "-XXLanguage:+InlineClasses"
        )
    }

}

tasks.withType<Test> {
    project.logger.lifecycle("Executing a test")
    testLogging {
        showStandardStreams = true
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "WaterSortPuzzleSolver"
            packageVersion = "1.0.0"
        }
    }
}