enableFeaturePreview("VERSION_CATALOGS")

rootProject.name = "test-kt-50410"


includeBuild("kmdc") {
  dependencySubstitution {
    substitute(module("dev.petuska:kmdc-textfield")).using(project(":kmdc:kmdc-textfield"))
  }
}
