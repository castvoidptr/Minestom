import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    application
    alias(libs.plugins.shadow)
}

dependencies {
    implementation(rootProject)

    runtimeOnly(libs.bundles.logback)
}

tasks {
    application {
        mainClass.set("net.deltamc.TestServer")
    }

    withType<ShadowJar> {
        archiveFileName.set("minestom.jar")
    }
}