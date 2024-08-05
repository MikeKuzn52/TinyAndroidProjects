plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    compileOnly(libs.symbol.processing.api)
    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.kotlinpoet)
    implementation(libs.kotlinpoet.ksp)

}