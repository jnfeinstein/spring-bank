import de.fayard.refreshVersions.bootstrapRefreshVersions

buildscript {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    dependencies { classpath("de.fayard.refreshVersions:refreshVersions:0.9.7") }
}
bootstrapRefreshVersions()

rootProject.name = "spring-bank"

include(
    "spring-bank-project:spring-bank-api",
    "spring-bank-project:spring-bank-client",
    "spring-bank-project:spring-bank-server"
)
