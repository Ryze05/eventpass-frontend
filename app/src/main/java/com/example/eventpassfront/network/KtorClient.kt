package com.example.eventpassfront.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.gson.gson

object KtorClient {
    val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            gson()
        }
    }

    const val BASE_URL = "http://10.0.2.2:8080/api"
}