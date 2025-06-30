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
import org.example.project.data.model.PlantIdentificationResult

/*
class ApiService(
    private val httpClient: HttpClient,
    private val apiKey: String
) {

    private val baseUrl = "https://api.plant.id/api/v3"

    suspend fun identifyPlant(request: IdentifyRequestBody): PlantIdentificationResult {
        val response: HttpResponse = httpClient.post("$baseUrl/identify") {
            contentType(ContentType.Application.Json)
            header("Api-Key", apiKey)
            setBody(request)
        }
        return response.body()
    }

    suspend fun assessPlantHealth(
        requestBody: HealthAssessmentRequestBody,
        language: String = "en",
        details: String = "all"
    ): HealthAssessmentResponse {
        val response: HttpResponse = httpClient.post("$baseUrl/health_assessment") {
            contentType(ContentType.Application.Json)
            header("Api-Key", apiKey)
            url {
                parameters.append("language", language)
                parameters.append("details", details)
            }
            setBody(requestBody)
        }
        return response.body()
    }
}


 */

