# PodciniLib

Contains API's and common routines for developing extensions for Podcini.A

## How to use:

Add it in your root settings.gradle at the end of repositories:

	dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}
	
In build.gradle:
	
	dependencies {
		        implementation 'com.github.XilinJia:PodciniLib:Tag'
	}
	