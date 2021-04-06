plugins {
    eclipse
    idea
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation(gradleApi())
    implementation("lt.petuska:npm-publish:1.1.3")
}
