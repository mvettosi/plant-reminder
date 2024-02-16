plugins {
  id("java-library")
  alias(libs.plugins.kotlin.jvm)
}

java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
  implementation(libs.javax.inject.javax.inject)

  testImplementation(libs.junit)
  testImplementation(libs.io.mockk.mockk)
  testImplementation(libs.org.jetbrains.kotlin.kotlin.test)
  testImplementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.test)
}
