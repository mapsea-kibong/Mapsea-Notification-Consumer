plugins {
    kotlin("jvm") version "2.3.21"
    kotlin("plugin.spring") version "2.3.21"
    kotlin("plugin.jpa") version "2.3.21"
    id("org.springframework.boot") version "3.5.7"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.mapsea"
version = "0.0.1-SNAPSHOT"
description = "Mapsea-Notification-Consumer"

kotlin {
    jvmToolchain(25)
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

repositories {
    mavenCentral()
}

extra["kotlinJdslVersion"] = "3.8.2"

dependencies {
    // Spring Boot Starters
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // Kafka
    implementation("org.springframework.kafka:spring-kafka")

    // AWS Secrets Manager
    implementation("io.awspring.cloud:spring-cloud-aws-starter-secrets-manager:3.1.1")

    // MySQL
    runtimeOnly("com.mysql:mysql-connector-j")

    // Kotlin JDSL (replaces QueryDSL — Kotlin-native type-safe JPA query DSL)
    implementation("com.linecorp.kotlin-jdsl:jpql-dsl:${property("kotlinJdslVersion")}")
    implementation("com.linecorp.kotlin-jdsl:jpql-render:${property("kotlinJdslVersion")}")
    implementation("com.linecorp.kotlin-jdsl:spring-data-jpa-support:${property("kotlinJdslVersion")}")

    // Cache
    implementation("com.github.ben-manes.caffeine:caffeine")

    // Redis connection pool
    implementation("org.apache.commons:commons-pool2:2.12.0")

    // UUID Generator
    implementation("com.fasterxml.uuid:java-uuid-generator:5.0.0")

    // Gmail OAuth (Google API — cloud-agnostic)
    implementation("com.google.oauth-client:google-oauth-client-jetty:1.36.0")
    implementation("com.google.apis:google-api-services-gmail:v1-rev110-1.25.0")
    implementation("com.google.api-client:google-api-client:1.30.9")

    // Jackson
    implementation("com.fasterxml.jackson.core:jackson-databind")

    // Test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testRuntimeOnly("com.h2database:h2")
}

// kotlin-jpa plugin: opens entity classes for JPA proxy + adds no-arg constructor
allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
