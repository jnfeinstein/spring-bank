plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
}

kotlin {
    jvm()

    js(IR) {
        nodejs()
        binaries.executable()
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

    sourceSets["jvmMain"].dependencies {
        implementation("org.axonframework:axon-modelling:${Versions.AXON_FRAMEWORK}")
    }
}
