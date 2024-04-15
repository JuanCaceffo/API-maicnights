import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.3"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.22"
	kotlin("plugin.spring") version "1.9.22"
	kotlin("plugin.jpa") version "1.9.22"
	jacoco
}

allOpen {
	annotation("jakarta.persistence.Entity")
}

group = "ar.edu.unsam.phm"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
	mavenCentral()
}

val mockkVersion = "1.13.9"
val kotestVersion = "5.8.0"

dependencies {
	developmentOnly("org.springframework.boot:spring-boot-devtools")

	// básicos de cualquier proyecto Spring Boot
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-hateoas")
	implementation("org.springframework.boot:spring-boot-starter-data-rest")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	// conexión a la base de datos
	runtimeOnly("org.postgresql:postgresql")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")

	// testing
	testImplementation("com.h2database:h2:2.2.224")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testImplementation("org.mockito:mockito-core:3.+")
	testImplementation("io.mockk:mockk:${mockkVersion}")
	testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
	testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")

	// esto ni idea.....
//	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr31 0")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.springdoc:springdoc-openapi-starter-common:2.2.0")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
	implementation(kotlin("stdlib"))

	// varios
	implementation("org.uqbar-project:geodds-xtend:1.0.3")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "21"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
	jvmArgs("-Xshare:off","-XX:+EnableDynamicAgentLoading")
}

tasks.test {
	finalizedBy(tasks.jacocoTestReport)
}

jacoco {
	toolVersion = "0.8.11"
}

tasks.jacocoTestReport {
	dependsOn(tasks.test)
	reports {
		xml.required.set(true)
		csv.required.set(true)
		html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
	}
}

tasks.register("runOnGitHub") {
	dependsOn("jacocoTestReport")
	group = "custom"
	description = "$ ./gradlew runOnGitHub # runs on GitHub Action"
}