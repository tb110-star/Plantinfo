package org.example.project.data.remote

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import io.ktor.client.statement.*
import io.ktor.http.*
import org.example.project.data.model.HealthAssessmentResponse
import org.example.project.data.model.PlantIdentificationResult
import org.example.project.data.model.RequestModel
import kotlinx.serialization.json.JsonElement


enum class AppLogLevel {
    DEBUG, INFO, WARN, ERROR
}

fun appLog(level: AppLogLevel, message: String) {
    println("[${level.name}] $message")
}

class ApiService {
    private val apiKey = "Jm1Fqb01dwZtjONk7zpNQ6aBw7gaOOUp0WWeKBLBhivW3JG7sV"
    private val baseUrl = "https://plant.id/api/v3"
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true

            })
        }
    }

    private val jsonFormatter = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    suspend fun identifyPlant(request: RequestModel): PlantIdentificationResult? {
        return try {
            val jsonBody = jsonFormatter.encodeToString(request)

            appLog(AppLogLevel.DEBUG, "identifyPlant request body:\n$jsonBody")
            appLog(AppLogLevel.DEBUG, "URL: ${"$baseUrl/identification"}")
            appLog(AppLogLevel.DEBUG, "Headers: Api-Key=$apiKey, Content-Type=application/json")
            appLog(AppLogLevel.INFO, "Sending identifyPlant request...")

            val response = client.post("${baseUrl}/identification") {
                contentType(ContentType.Application.Json)
                header("Api-Key", apiKey)

                url {
                    parameters.append("details", "common_names,url,description,taxonomy,rank,gbif_id,inaturalist_id,image,synonyms,edible_parts,watering,propagation_methods")
                    parameters.append("language", "en")
                }

                setBody(jsonBody)
            }

            appLog(AppLogLevel.DEBUG, "identifyPlant response status: ${response.status}")

            val rawJson = response.bodyAsText()
            appLog(AppLogLevel.DEBUG, "Raw JSON response:\n$rawJson")

            // pretty print
            val jsonElement = jsonFormatter.parseToJsonElement(rawJson)
            val prettyJson = jsonFormatter.encodeToString(JsonElement.serializer(), jsonElement)
            appLog(AppLogLevel.DEBUG, "Pretty JSON response:\n$prettyJson")

            val body = jsonFormatter.decodeFromString<PlantIdentificationResult>(rawJson)
            appLog(AppLogLevel.INFO, "identifyPlant success")
            body

        } catch (e: Exception) {
            appLog(AppLogLevel.ERROR, "identifyPlant failed: ${e.message}")
            null
        }
    }

    suspend fun assessPlantHealth(request: RequestModel): HealthAssessmentResponse? {
        return try {
            val jsonBody = jsonFormatter.encodeToString(request)

            appLog(AppLogLevel.DEBUG, "assessPlantHealth request body:\n$jsonBody")
            appLog(AppLogLevel.DEBUG, "URL: ${"$baseUrl/health_assessment"}")
            appLog(AppLogLevel.DEBUG, "Headers: Api-Key=$apiKey, Content-Type=application/json")
            appLog(AppLogLevel.INFO, "Sending assessPlantHealth request...")

            val response = client.post("${baseUrl}/health_assessment") {
                contentType(ContentType.Application.Json)
                header("Api-Key", apiKey)

                url {
                    parameters.append("language", "en")
                    parameters.append("details", "all")
                }

                setBody(jsonBody)
            }

            appLog(AppLogLevel.DEBUG, "assessPlantHealth response status: ${response.status}")

            val rawJson = response.bodyAsText()
            appLog(AppLogLevel.DEBUG, "Raw JSON response:\n$rawJson")

            val jsonElement = jsonFormatter.parseToJsonElement(rawJson)
            val prettyJson = jsonFormatter.encodeToString(JsonElement.serializer(), jsonElement)
            appLog(AppLogLevel.DEBUG, "Pretty JSON response:\n$prettyJson")

            val body = jsonFormatter.decodeFromString<HealthAssessmentResponse>(rawJson)
            appLog(AppLogLevel.INFO, "assessPlantHealth success")
            body

        } catch (e: Exception) {
            appLog(AppLogLevel.ERROR, "assessPlantHealth failed: ${e.message}")
            null
        }
    }
}
