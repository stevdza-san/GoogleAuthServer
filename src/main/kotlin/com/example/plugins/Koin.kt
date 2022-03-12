package com.example.plugins

import com.example.di.koinModule
import io.ktor.server.application.*
import io.ktor.util.*
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

fun Application.configureKoin() {
    install(CustomKoinPlugin){
        modules(koinModule)
    }
}

internal class CustomKoinPlugin(internal val koinApplication: KoinApplication) {
    // Implements ApplicationPlugin as a companion object.
    companion object Plugin : ApplicationPlugin<ApplicationCallPipeline, KoinApplication, CustomKoinPlugin> {
        // Creates a unique key for the plugin.
        override val key = AttributeKey<CustomKoinPlugin>("CustomKoinPlugin")

        // Code to execute when installing the plugin.
        override fun install(
            pipeline: ApplicationCallPipeline,
            configure: KoinApplication.() -> Unit
        ): CustomKoinPlugin {
            val koinApplication = startKoin(appDeclaration = configure)
            return CustomKoinPlugin(koinApplication)
        }
    }
}