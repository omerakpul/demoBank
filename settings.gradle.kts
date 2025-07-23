pluginManagement {
    repositories {
        google()
        mavenCentral() // Burası şart!
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "DemoBankApp"
include(":app")
 