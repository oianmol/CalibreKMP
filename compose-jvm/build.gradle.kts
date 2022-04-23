import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
  kotlin("multiplatform")
  id("org.jetbrains.compose") version ComposeDesktopDependencyVersions.composeDesktopWeb
}

group = "com.mutualmobile"
version = "1.0"

repositories {
  mavenCentral()
  maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

allprojects {
  tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
    kotlinOptions {
      freeCompilerArgs = freeCompilerArgs +  listOf("-Xuse-experimental=kotlin.ExperimentalUnsignedTypes",
        "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi",
        "-Xuse-experimental=kotlinx.coroutines.InternalCoroutinesApi",
        "-Xuse-experimental=androidx.compose.animation.ExperimentalAnimationApi",
        "-Xuse-experimental=androidx.compose.ExperimentalComposeApi",
        "-Xuse-experimental=androidx.compose.material.ExperimentalMaterialApi",
        "-Xuse-experimental=androidx.compose.runtime.ExperimentalComposeApi",
        "-Xuse-experimental=androidx.compose.ui.ExperimentalComposeUiApi",
        "-Xuse-experimental=coil.annotation.ExperimentalCoilApi",
        "-Xuse-experimental=kotlinx.serialization.ExperimentalSerializationApi",
        "-Xuse-experimental=com.google.accompanist.pager.ExperimentalPagerApi")
    }
  }
}

kotlin {
  jvm {
    compilations.all {
      kotlinOptions.jvmTarget = "11"
    }
    withJava()
  }
  sourceSets {
    val jvmMain by getting {
      dependencies {
        implementation(project(":shared"))
        implementation("com.google.code.gson:gson:2.9.0")
        implementation(compose.desktop.currentOs)
      }
    }
    val jvmTest by getting
  }
}

compose.desktop {
  application {
    mainClass = "MainKt"
    nativeDistributions {
      targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
      packageName = "jvm"
      packageVersion = "1.0.0"
    }
  }
}