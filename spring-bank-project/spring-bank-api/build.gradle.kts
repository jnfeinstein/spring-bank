plugins {
    id("lt.petuska.npm.publish")
    kotlin("multiplatform")
    kotlin("plugin.serialization")
}

kotlin {
    jvm()

    js(IR) {
        moduleName = project.name
        nodejs()
        binaries.library()
    }

    sourceSets {
        all {
            languageSettings.useExperimentalAnnotation("kotlin.RequiresOptIn")
            languageSettings.useExperimentalAnnotation("kotlin.js.ExperimentalJsExport")
            languageSettings.useExperimentalAnnotation("kotlinx.serialization.ExperimentalSerializationApi")

            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-protobuf:1.1.0")
            }
        }
    }
}

npmPublishing {
    organization = "jnfeinstein"

    publications {
        this["js"].packageJson {
            repository {
                url = "git://github.com/jnfeinstein/spring-bank.git"
            }
        }
    }

    repositories {
        repository("github") {
            registry = uri("https://npm.pkg.github.com")
            authToken = System.getenv("GITHUB_AUTH_TOKEN")?.trim() ?: ""
        }
    }
}
