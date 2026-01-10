plugins {
    `java-library`
    id("com.gradleup.shadow") version "9.3.1"
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.19"
}

group = "net.opmasterleo"
version = "2.1"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io")
}

dependencies {
    paperweight.paperDevBundle("1.21.11-R0.1-SNAPSHOT")

    compileOnly("org.jetbrains:annotations:26.0.2")

    implementation("com.github.Anon8281:UniversalScheduler:0.1.7")
}

tasks {
    withType<JavaCompile> {
        options.release.set(21)
        options.encoding = "UTF-8"
    }

    shadowJar {
        archiveFileName.set("${project.name}-v${project.version}.jar")

        relocate("com.github.Anon8281.universalScheduler", "net.opmasterleo.playtimed.universalScheduler")
        
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