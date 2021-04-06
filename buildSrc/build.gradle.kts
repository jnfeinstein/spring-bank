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
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.32")
}
