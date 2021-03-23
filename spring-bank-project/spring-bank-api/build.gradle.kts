plugins {
    kotlin("multiplatform")
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
        }
    }

    sourceSets["jvmMain"].dependencies {
        implementation("org.axonframework:axon-modelling:${Versions.AXON_FRAMEWORK}")
    }
}
