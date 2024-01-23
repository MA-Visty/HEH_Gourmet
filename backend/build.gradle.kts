import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

plugins {
    java
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "be.heh"
version = "0.0.1"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    all {
        exclude(group = "commons-logging", module = "commons-logging")
    }
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // cloudinary dependencies
    implementation("com.cloudinary:cloudinary-http45:1.29.0")

    // stripe dependencies
    implementation("com.stripe:stripe-java:24.11.0")

    // spring dependencies
    runtimeOnly("org.postgresql:postgresql")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

abstract class DockerBuild : DefaultTask() {
    @Input
    var dockerhubUser = ""

    @Input
    val imageName = project.name.lowercase()

    @Input
    val imageVersion = project.version

    @TaskAction
    fun dockerBuild() {
        val name: String = if (this.dockerhubUser == "") {
            this.imageName + ":" + this.imageVersion
        } else {
            this.dockerhubUser + "/" + this.imageName + ":" + this.imageVersion
        }

        val processBuilder = ProcessBuilder("docker", "build", "--build-arg", "JAR_FILE=build/libs/"
                + project.name + "-" + project.version + ".jar", "--label", this.imageName, "-t", name, ".")
        processBuilder.redirectErrorStream(true)
        val process = processBuilder.start()
        val future: Future<Int> = Executors.newSingleThreadExecutor().submit(Callable {
            val inputStream: InputStream = process.inputStream
            val inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                println(line)
            }
            return@Callable 0
        })
        try {
            future.get(5, TimeUnit.MINUTES)
        } catch (e: Exception) {
            println("Timeout")
        }
        process.waitFor()

        ProcessBuilder("docker", "image", "prune", "--force").start().waitFor()
    }
}

tasks.register<DockerBuild>("docker-build") {
    dockerhubUser = System.getenv("DOCKERHUB_USER") ?: ""
    dependsOn("assemble")
}

abstract class DockerPush : DefaultTask() {
    @Input
    var dockerhubUser = ""

    @Input
    val imageName = project.name.lowercase()

    @Input
    val imageVersion = project.version

    @TaskAction
    fun dockerPush() {
        if (this.dockerhubUser == "") {
            pushImage(this.imageName + ":" + this.imageVersion)
        } else {
            pushImage(this.dockerhubUser + "/" + this.imageName + ":" + this.imageVersion)
        }

        ProcessBuilder("docker", "image", "prune", "--force").start().waitFor()
    }

    fun pushImage(name: String) {
        val processBuilder = ProcessBuilder("docker", "push", name)
        processBuilder.redirectErrorStream(true)
        val process = processBuilder.start()
        val future: Future<Int> = Executors.newSingleThreadExecutor().submit(Callable {
            val inputStream: InputStream = process.inputStream
            val inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                println(line)
            }
            return@Callable 0
        })
        try {
            future.get(5, TimeUnit.MINUTES)
        } catch (e: Exception) {
            println("Timeout")
        }
        process.waitFor()
    }
}

tasks.register<DockerPush>("docker-push") {
    dockerhubUser = System.getenv("DOCKERHUB_USER")
            ?: throw GradleException("DOCKERHUB_USER environment variable is not set")
    dependsOn("docker-build")
}