import org.jetbrains.compose.compose

plugins {
  kotlin("multiplatform") version "1.6.10"
  id("org.jetbrains.compose") version "1.0.1"
  // https://youtrack.jetbrains.com/issue/KT-50410
  // commenting out the kvision plugin fixes the issue
  id("io.kvision") version "5.6.1"
}

group = "com.rocketraman.testkt50410"
version = "1.0"

val kvisionVersion = "5.6.1"

repositories {
  google()
  maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
  mavenCentral()
}

kotlin {
  jvm("backend") {
    withJava()
    testRuns["test"].executionTask.configure {
      useJUnitPlatform()
    }
  }
  js("frontend", IR) {
    binaries.executable()
    browser {
      runTask {
        sourceMaps = true
      }
      commonWebpackConfig {
        cssSupport.enabled = true
      }
    }
  }
  sourceSets {
    val commonMain by getting {
      dependencies {
        api("io.kvision:kvision-server-ktor:$kvisionVersion") {
          exclude("ch.qos.logback")
        }
        api(compose.runtime)
        api(compose.web.core)
      }
    }
    val commonTest by getting {
      dependencies {
        implementation(kotlin("test"))
      }
    }
    val frontendMain by getting {
      dependencies {
        implementation("dev.petuska:kmdc-textfield:0.0.2")
      }
    }
    val frontendTest by getting
  }
}

// https://youtrack.jetbrains.com/issue/KT-50410
// To workaround the issue need to uncomment both here and in kmdc/buildSrc/src/main/kotlin/plugin.library-mpp.gradle.kts

/*
afterEvaluate {
    rootProject.extensions.configure<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension> {
        versions.webpackDevServer.version = "4.6.0"
        versions.webpack.version = "5.65.0"
        versions.webpackCli.version = "4.9.1"
    }
}
*/
