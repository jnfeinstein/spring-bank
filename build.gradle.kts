import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinCompileCommon

plugins {
	base
	id("com.palantir.docker") version Versions.GRADLE_DOCKER apply false
	id("io.spring.dependency-management") version Versions.GRADLE_SPRING apply false
	id("org.jmailen.kotlinter") version Versions.GRADLE_KTLINT
	id("org.springframework.boot") version Versions.SPRING_BOOT apply false
	kotlin("jvm") version Versions.KOTLIN apply false
	kotlin("multiplatform") version Versions.KOTLIN  apply false
	kotlin("plugin.spring") version Versions.KOTLIN  apply false
}

allprojects {
	apply(plugin = "org.jmailen.kotlinter")

	repositories {
		mavenCentral()
	}

	group = "io.joel"
	version = "0.0.1-SNAPSHOT"

	kotlinter {
		experimentalRules = true
		ignoreFailures = true
	}
}

subprojects {
	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs += listOf("-Xjsr305=strict", "-Xopt-in=kotlin.RequiresOptIn")
			jvmTarget = "11"
			useIR = true
		}
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}