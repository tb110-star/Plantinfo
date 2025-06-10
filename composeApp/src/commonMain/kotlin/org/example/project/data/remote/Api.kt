package org.example.project.data.remote
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
 class ApiService{

     private val client = HttpClient(CIO) {
         install(ContentNegotiation) {
             json(Json {
                 ignoreUnknownKeys = true
                 prettyPrint = true
             })
         }
     }
     suspend fun getPlantInfo(): String {
         return client.get("https://api.plant.id/v2/identify") {
             headers {
                 append(HttpHeaders.Authorization, "Bearer YOUR_API_KEY")
                 append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
             }
         }.body()
     }
 }