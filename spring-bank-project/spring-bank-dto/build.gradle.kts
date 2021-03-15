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
        }
    }
}