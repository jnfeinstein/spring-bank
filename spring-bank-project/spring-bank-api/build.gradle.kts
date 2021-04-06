plugins {
  id("springbank.npm-conventions")
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
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-protobuf:_")
      }
    }
  }
}
