plugins {
  `maven-publish`
}

version = "0.0.1"
description = """
  Benkemannen, a fork of botamusique
"""

task<Exec>("build.benkemannen") {
  description = "build benkemannen"
  commandLine("docker")
  args("build", ".", "-t", "benkemannen:latest")
  inputs.files(fileTree("./lang"), fileTree("./scripts"), fileTree("./web"), fileTree("./configuration.ini"), fileTree("pymumble"))
    .withPropertyName("sourceFiles")
    .withPathSensitivity(PathSensitivity.RELATIVE)
}

task("build") {
  dependsOn(tasks.withType<Exec>())
}

task<Exec>("zipper") {
  mkdir("target")
  commandLine("docker")
  args("save", "-o", "target/benkemannen.tar.gz", "benkemannen:latest")
}

val docker by configurations.creating
val repoUser: String? by project
val repoPassword: String? by project
val artifact = artifacts.add("docker", file("target/benkemannen.tar.gz")) {
  type = "tarball"
  classifier = "prod"
  extension = "tar.gz"
  name = "benkemannen"
}

publishing {
  publications {
    create<MavenPublication>("benkemannen") {
      groupId = "qwde.benkemannen"
      description = "benkemannen"
      artifact(artifact)
      artifactId = "benkemannen"
      pom {
        name.set("benkemannen")
        description.set("Docker image to host a botamusique fork")
        url.set("http://qwde.no:8181")
        developers {
          developer {
            id.set("andsild")
            name.set("Anders")
            email.set("trolo@lol.lol")
          }
        }
      }
    }
  }
  repositories {
    maven {
      url = uri("https://qwde.no/archiva/repository/internal/")
      credentials {
          // Store in ~/.gradle/gradle.properties
          username = "admin"
          password = repoPassword
      }
    }
  }
}
