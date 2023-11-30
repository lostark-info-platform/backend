import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.3"
    id("io.spring.dependency-management") version "1.1.3"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
    kotlin("plugin.jpa") version "1.8.22"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
    jacoco
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

jacoco {
    toolVersion = "0.8.9"
}

group = "org.info"
version = "0.0.1-SNAPSHOT"

java {
}

repositories {
    mavenCentral()
}

val asciidoctorExt: Configuration by configurations.creating
val jjwtVersion = "0.11.5"
val kotestVersion = "5.6.2"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("io.jsonwebtoken:jjwt-api:$jjwtVersion")
    implementation("io.jsonwebtoken:jjwt-impl:$jjwtVersion")
    implementation("io.jsonwebtoken:jjwt-jackson:$jjwtVersion")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-mysql")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("com.mysql:mysql-connector-j")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    // kotest
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.3")
    // https://mvnrepository.com/artifact/it.ozimov/embedded-redis
    implementation("it.ozimov:embedded-redis:0.7.2")
    // mockkBean with mockk
    testImplementation("com.ninja-squad:springmockk:4.0.2")
    // spring-rest-docs
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    asciidoctorExt("org.springframework.restdocs:spring-restdocs-asciidoctor")
    implementation(kotlin("stdlib-jdk8"))

    testImplementation("org.springframework.security:spring-security-test")
}

val snippetsDir by extra { file("build/generated-snippets") }

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "17"
        }
    }
    withType<Test> {
        useJUnitPlatform()
        finalizedBy(jacocoTestReport)
    }
    // spring-restdocs-mockmvc를 통해 만들어진 파일을 index 파일로 생성 (템플릿은 src/docs/asciidoc/index.adoc에 구현)
    asciidoctor {
        dependsOn(test)
        configurations("asciidoctorExt")
        baseDirFollowsSourceFile()
        inputs.dir(snippetsDir)
    }
    // 만들어진 index 파일을 로컬에서 확인가능하게 카피
    register<Copy>("copyDocument") {
        dependsOn(asciidoctor)
        from("${asciidoctor.get().outputDir}/index.html")
        into("src/main/resources/static/docs")
    }
    // 만들어진 index 파일을 jar파일안에 삽입
    bootJar {
        dependsOn("copyDocument")
        dependsOn("copyDocument")
        from("${asciidoctor.get().outputDir}/index.html") {
            into("static/docs")
        }
    }
    // jacoco 적용
    jacocoTestCoverageVerification {
        violationRules {
            rule {
                limit {
                    counter = "LINE"
                    value = "COVEREDRATIO"
                    minimum = "0.50".toBigDecimal()
                }
            }
        }
    }
    jacocoTestReport {
        dependsOn(test)
        finalizedBy(jacocoTestCoverageVerification)
    }
}
kotlin {
    jvmToolchain(17)
}