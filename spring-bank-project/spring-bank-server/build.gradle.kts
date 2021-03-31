plugins {
    id("com.palantir.docker")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
}

java.sourceCompatibility = JavaVersion.VERSION_11

dependencies {
    implementation(project(":spring-bank-project:spring-bank-api"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("io.github.microutils:kotlin-logging-jvm:${Versions.KOTLIN_LOGGING}")
    implementation("org.axonframework:axon-spring-boot-starter:${Versions.AXON_FRAMEWORK}") {
        exclude(group = "org.axonframework", module = "axon-server-connector")
    }
    implementation("org.flywaydb:flyway-core")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-protobuf:1.1.0")
    implementation("io.r2dbc:r2dbc-postgresql")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
}

tasks.assemble {
    dependsOn(tasks.docker)
}

docker {
    name = "spring-bank/${project.name}"
    setDockerfile(file("configuration/docker/Dockerfile"))
    files(tasks.bootJar.get().archiveFile)
    buildArgs(
        mapOf(
            "JAR_FILE" to tasks.bootJar.get().archiveFileName.get()
        )
    )
}