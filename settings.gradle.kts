pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        maven(url = "https://jitpack.io") // JitPack plugin 지원 필요 시
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { setUrl(uri("/Users/yuchangsoo/local-maven-repo")) }
        maven(url = "https://jitpack.io")
    }
}

rootProject.name = "TJLabsVenus-sdk-android"
include(":app")
 