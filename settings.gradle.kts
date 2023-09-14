@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "Maps and Location sample"

include(":app")
include(":core-data")
include(":core-database")
include(":core-testing")
include(":core-ui")
include(":feature-userlocation")
include(":test-app")
include(":core-geocoding")
include(":core-common")
include(":core-model")
