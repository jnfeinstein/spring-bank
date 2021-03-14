plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm()

    js(IR) {
        browser()
        binaries.executable()
    }

    sourceSets {
        all {
            languageSettings.useExperimentalAnnotation("kotlin.RequiresOptIn")
        }
    }
}