plugins {
  `kotlin-dsl`
}

repositories {
  mavenLocal()
  gradlePluginPortal()
  mavenCentral()
}

dependencies {
  implementation(gradleApi())
  implementation("lt.petuska:npm-publish:_")
  implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:_")
}
