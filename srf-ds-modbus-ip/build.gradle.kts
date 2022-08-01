plugins {
    kotlin("jvm") version "1.6.21"
}

group = "org.reggster"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        url = uri("https://maven.mangoautomation.net/repository/ias-release/")
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation("com.infiniteautomation:modbus4j:3.1.0")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}