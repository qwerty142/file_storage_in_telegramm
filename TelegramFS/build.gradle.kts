plugins {
    id("java")
}

group = "ru.tgfs"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.github.serceman:jnr-fuse:0.5.7")
}

tasks.register<Jar>("uberJar") {
    description = "Make JAR with all the dependencies included"
    archiveClassifier.set("uber")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    dependsOn(configurations.runtimeClasspath.get())
    from(sourceSets.main.get().output)
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}
