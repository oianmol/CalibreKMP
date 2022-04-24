pluginManagement {
  repositories {
    google()
    gradlePluginPortal()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    mavenCentral()
  }
}

rootProject.name = "BaseiOKMM"
include(":shared")
include(":compose-jvm")