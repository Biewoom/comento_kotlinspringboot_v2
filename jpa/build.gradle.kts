
plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
    kotlin("plugin.allopen")
    kotlin("plugin.noarg")
}

val KOTEST_VERSION = "5.3.0"

tasks.getByName<Test>("test") {
    useJUnitPlatform()
    testLogging {
        events(
                org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED,
                org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED,
                org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_ERROR,
                org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED
        )
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        showExceptions = true
        showCauses = true
        showStackTraces = true
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    compileOnly("com.h2database:h2")
    runtimeOnly("mysql:mysql-connector-java")
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
    testImplementation("org.springframework.boot:spring-boot-starter-test"){
        exclude(module = "mockito-core")
    }

    // open API
    implementation("org.springdoc:springdoc-openapi-data-rest:1.5.12")
    implementation("org.springdoc:springdoc-openapi-kotlin:1.5.12")

    // KLogger
    implementation("io.github.microutils:kotlin-logging:2.1.21")

    // log4j2
    implementation("org.springframework.boot:spring-boot-starter-log4j2")

    modules {
        module("org.springframework.boot:spring-boot-starter-logging") {
            replacedBy("org.springframework.boot:spring-boot-starter-log4j2", "Use Log4j2 instead of Logback")
        }
    }


    implementation("com.github.consoleau:kassava:2.1.0")

    // h2 DB for Test
    testRuntimeOnly("com.h2database:h2")

    // mockk
    testImplementation("io.mockk:mockk:1.9.3")
    // Kotest
    testImplementation("io.kotest:kotest-runner-junit5:${KOTEST_VERSION}")
    testImplementation("io.kotest:kotest-assertions-core:${KOTEST_VERSION}")

    modules {
        module("org.springframework.boot:spring-boot-starter-logging") {
            replacedBy("org.springframework.boot:spring-boot-starter-log4j2", "Use Log4j2 instead of Logback")
        }
    }

    allOpen {
        annotation("javax.persistence.Entity")
    }

    noArg {
        annotation("javax.persistence.Entity")
    }
}


configurations {
    all {
        exclude("ch.qos.logback", "logback-classic")
        exclude("org.apache.logging.log4j", "log4j-to-slf4j")
    }
}