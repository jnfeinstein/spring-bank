import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    base
    id("org.jmailen.kotlinter")
    idea
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

allprojects {
    apply(plugin = "org.jmailen.kotlinter")

    repositories {
        mavenCentral()
    }

    kotlinter {
        experimentalRules = true
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
