plugins {
    `java-library`
    id("com.gradleup.shadow") version "9.3.1"
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.19"
}

group = "net.opmasterleo"
version = "3.2"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

dependencies {
    paperweight.paperDevBundle("1.21.11-R0.1-SNAPSHOT")

    compileOnly("org.jetbrains:annotations:26.0.2")
    compileOnly("me.clip:placeholderapi:2.11.6")
}

tasks {
    withType<JavaCompile> {
        options.release.set(21)
        options.encoding = "UTF-8"
    }

    shadowJar {
        archiveFileName.set("${project.name}-v${project.version}.jar")
        
        configurations = listOf(project.configurations.getByName("runtimeClasspath"))
    }

    assemble {
        dependsOn(shadowJar)
    }

processResources {
        val props = mapOf(
            "name" to project.name,
            "version" to project.version,
            "description" to (project.description ?: "PlayTimed plugin") 
        )
        
        inputs.properties(props)
        filteringCharset = "UTF-8"

        filesMatching("**/plugin.yml") {
            expand(props)
        }
    }
}