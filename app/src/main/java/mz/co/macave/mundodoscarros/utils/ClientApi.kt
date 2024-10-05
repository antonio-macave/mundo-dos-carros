package mz.co.macave.mundodoscarros.utils

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.gson.gson


val client = HttpClient(CIO) {
    install(ContentNegotiation) {
        gson()
    }
    install(Logging) {
        level = LogLevel.ALL
    }
}