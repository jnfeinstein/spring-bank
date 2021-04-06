plugins {
    id("springbank.npm-conventions")
    kotlin("multiplatform")
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

            dependencies {
                api(project(":spring-bank-project:spring-bank-api"))
                implementation("io.ktor:ktor-client-websockets:1.5.2")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
            }
        }
    }
}
