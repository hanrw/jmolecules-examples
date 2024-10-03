import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jmolecules.bytebuddy.JMoleculesPlugin

buildscript {
    dependencies {
        classpath(platform("org.jmolecules:jmolecules-bom:2023.1.4"))
        classpath("org.jmolecules.integrations:jmolecules-bytebuddy")
        classpath("org.jmolecules:kmolecules-ddd")
        classpath("org.jmolecules.integrations:jmolecules-spring")
        classpath("org.jmolecules.integrations:jmolecules-jpa")
    }
}

plugins {
    id("org.jetbrains.kotlin.plugin.allopen") version "2.0.20"
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
    kotlin("jvm") version "2.0.20"
    id("org.jetbrains.kotlinx.kover") version "0.5.1"
    kotlin("plugin.spring") version "2.0.20"
    kotlin("plugin.jpa") version "2.0.20"
    id("net.bytebuddy.byte-buddy-gradle-plugin") version "1.15.3"
}

group = "com.tddworks"

version = "0.0.1-SNAPSHOT"


val ktlint by configurations.creating

repositories { mavenCentral() }

dependencies {
    implementation(platform("org.jmolecules:jmolecules-bom:2023.1.4"))
    implementation("net.bytebuddy:byte-buddy-gradle-plugin:1.12.10")
    implementation("org.jmolecules.integrations:jmolecules-bytebuddy-nodep")
    implementation("org.jmolecules.integrations:jmolecules-spring")
    implementation("org.jmolecules.integrations:jmolecules-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.session:spring-session-jdbc")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jmolecules:kmolecules-ddd")

    implementation("org.joda:joda-money:1.0.1")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-joda-money:2.13.3")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("org.springdoc:springdoc-openapi-ui:1.8.0")
    implementation("org.springdoc:springdoc-openapi-kotlin:1.8.0")
    implementation("io.hypersistence:hypersistence-utils-hibernate-63:3.8.3")
    compileOnly("org.projectlombok:lombok")

    runtimeOnly("mysql:mysql-connector-java:8.0.29")
    implementation("com.h2database:h2:2.1.212")

    testImplementation("org.mockito.kotlin:mockito-kotlin:5.4.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // https://mvnrepository.com/artifact/com.pinterest.ktlint/ktlint-core
    ktlint("com.pinterest:ktlint:0.46.1") {
        attributes {
            attribute(
                Bundling.BUNDLING_ATTRIBUTE,
                objects.named(Bundling.EXTERNAL)
            )
        }
    }
}

tasks {

    byteBuddy {
        transformation {
            plugin = JMoleculesPlugin::class.java
        }
    }

    register<JavaExec>("ktlintCheck") {
        description = "Check Kotlin code style."
        classpath = ktlint
        mainClass.set("com.pinterest.ktlint.Main")
        args = listOf("src/**/*.kt")
    }

    register<JavaExec>("ktlintFormat") {
        description = "Fix Kotlin code style deviations."
        classpath = ktlint
        mainClass.set("com.pinterest.ktlint.Main")
        args = listOf("-F", "src/**/*.kt")
        jvmArgs = listOf("--add-opens", "java.base/java.lang=ALL-UNNAMED")
    }

    compileKotlin {
        compilerOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }
}
