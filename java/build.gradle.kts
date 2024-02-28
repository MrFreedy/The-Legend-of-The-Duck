plugins {
    id("java")
    id("checkstyle")
}

group = "org.azal"
version = "1.0-SNAPSHOT"

apply(plugin = "checkstyle")

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

checkstyle {
    toolVersion = "8.45"
    configFile = file("config/checkstyle/checkstyle.xml")
    val mainSourceSet = project.sourceSets.getByName("main")
    sourceSets = listOf(mainSourceSet)
}

tasks.withType<Checkstyle>().configureEach {
    configFile = file("config/checkstyle/checkstyle.xml")
    ignoreFailures = false
}