plugins {
    id("org.jetbrains.intellij") version "0.6.5"
    kotlin("jvm") version "1.3.70"
}

sourceSets {
    main {
        java.srcDirs("src/main/kotlin")
    }
}

group = "org.hoshino9.anti.indulged"
version = "0.0.2"

val intellijPublishToken: String? by ext
val coroutinesVersion: String by ext

repositories {
    maven("https://maven.aliyun.com/repository/public")
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8", "1.3.70"))
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core", coroutinesVersion)
    testImplementation(kotlin("test-junit"))
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version = "2020.2.3"
    instrumentCode = true

}

tasks["buildSearchableOptions"].enabled = false

tasks.getByName<org.jetbrains.intellij.tasks.PublishTask>("publishPlugin") {
    channels("stable")
    token(intellijPublishToken)
}

tasks.getByName<org.jetbrains.intellij.tasks.PatchPluginXmlTask>("patchPluginXml") {
    val root = "src/main/resources"

    sinceBuild("202")
    untilBuild("514.*")

    version(rootProject.version)
    changeNotes(file("$root/change-notes.html").readText())
    pluginDescription(file("$root/description.html").readText())
}