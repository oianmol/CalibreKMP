plugins {
    CommonPlugins.plugins.forEach { dependency ->
        id(dependency)
    }
    CommonPlugins.kotlinPlugins.forEach { dependency ->
        kotlin(dependency)
    }
}

version = "1.0"

kotlin {
    targets{
        jvm() {
            compilations.all {
                kotlinOptions.jvmTarget = "11"
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                CommonMainDependencies.implementation.forEach(::implementation)
                CommonMainDependencies.api.forEach(::api)
            }
        }
        val commonTest by getting {
            dependencies {
                CommonTestDependencies.implementation.forEach(::implementation)
                CommonTestDependencies.kotlin.map { dependency ->
                    kotlin(dependency)
                }.forEach(::implementation)
            }
        }

        val jvmMain by getting {
            dependencies {
                ComposeDesktopDependencies.implementation.forEach(::implementation)
            }
        }
    }
}


tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}


sqldelight {
    database("BaseIoDB") {
        packageName = "com.baseio.kmm.db"
    }
}