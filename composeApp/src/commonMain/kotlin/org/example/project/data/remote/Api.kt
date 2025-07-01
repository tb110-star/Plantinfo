package org.example.project.data.remote
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.example.project.data.model.HealthAssessmentResponse
import org.example.project.data.model.PlantIdentificationResult
import org.example.project.data.model.RequestModel

import org.example.project.secret.Secrets

class ApiService(
) {
    private val apiKey: String = Secrets.plantIdApiKey
    private val baseUrl = "https://api.plant.id/api/v3"
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
            })
        }
    }
    suspend fun identifyPlant(request: RequestModel): PlantIdentificationResult {
        val response: HttpResponse = client.post("$baseUrl/identify") {
            contentType(ContentType.Application.Json)
            header("Api-Key", apiKey)
            setBody(request)
        }
        return response.body()
    }

    suspend fun assessPlantHealth(request: RequestModel ): HealthAssessmentResponse {
        val response: HttpResponse = client.post("$baseUrl/health_assessment") {
            contentType(ContentType.Application.Json)
            header("Api-Key", apiKey)
           url {
            parameters.append("language", "en")
            parameters.append("details", "all")
        }

            setBody(request)
        }
        return response.body()
    }
}



