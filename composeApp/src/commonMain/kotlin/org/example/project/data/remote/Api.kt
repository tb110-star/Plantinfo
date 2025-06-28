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
import io.ktor.client.statement.*
import io.ktor.http.*
import org.example.project.data.model.PlantIdentificationResult

/*
 class ApiService{

     private val client = HttpClient(CIO) {
         install(ContentNegotiation) {
             json(Json {
                 ignoreUnknownKeys = true
                 prettyPrint = true
             })
         }
     }

     companion object {
         suspend fun getPlantInfo(apiService: ApiService): String {
             return apiService.client.post("https://api.plant.id/v2/identify") {
                 headers {
                     append(HttpHeaders.Authorization, "Bearer Jm1Fqb01dwZtjONk7zpNQ6aBw7gaOOUp0WWeKBLBhivW3JG7sV")
                     append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                 }
             }.body()
         }
     }
 }

 */
/*
class ApiService(
    private val httpClient: HttpClient,
    private val apiKey: String
) {

    private val baseUrl = "https://api.plant.id/api/v3"

    suspend fun identifyPlant(requestBody: IdentifyRequestBody): PlantIdentificationResult {
        val response: HttpResponse = httpClient.post("$baseUrl/identify") {
            contentType(ContentType.Application.Json)
            header("Api-Key", apiKey)
            setBody(requestBody)
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