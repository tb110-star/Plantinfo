package org.example.project.data.remote

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import io.ktor.client.statement.*
import io.ktor.http.*
import org.example.project.Secrets
import org.example.project.data.model.HealthAssessmentResponse
import org.example.project.data.model.PlantIdentificationResult
import org.example.project.data.model.RequestModel
enum class AppLogLevel {
    DEBUG, INFO, ERROR
}

fun appLog(level: AppLogLevel, message: String) {
    println("[${level.name}] $message")
}

class ApiService {
    private val apiKey = Secrets.PLANT_API_KEY
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

 //   suspend fun identifyPlant(request: RequestModel): PlantIdentificationResult? {
      suspend fun identifyPlant(request: RequestModel): Result<PlantIdentificationResult>{

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
        return when (response.status) {
            HttpStatusCode.OK,
            HttpStatusCode.Created -> {
                val result = jsonFormatter.decodeFromString<PlantIdentificationResult>(rawJson)
                appLog(AppLogLevel.INFO, "identifyPlant success")
                Result.success(result)
            }

            HttpStatusCode.Unauthorized -> {
                Result.failure(Exception("Unauthorized: API key invalid"))
            }

            HttpStatusCode.InternalServerError -> {
                Result.failure(Exception("Server Error: Try again later"))
            }

            else -> {
                Result.failure(Exception("Unexpected error: ${response.status}"))
            }
        }

        } catch (e: Exception) {
            appLog(AppLogLevel.ERROR, "identifyPlant failed: ${e.message}")
          //  null
        Result.failure(e)

    }
    }

  //  suspend fun assessPlantHealth(request: RequestModel): HealthAssessmentResponse? {
  suspend fun assessPlantHealth(request: RequestModel):Result<HealthAssessmentResponse> {

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
          return when (response.status) {
              HttpStatusCode.OK,
              HttpStatusCode.Created -> {
                  val result = jsonFormatter.decodeFromString<HealthAssessmentResponse>(rawJson)
                  appLog(AppLogLevel.INFO, "identifyHealth success")
                  Result.success(result)
              }

              HttpStatusCode.Unauthorized -> {
                  Result.failure(Exception("Unauthorized: API key invalid"))
              }

              HttpStatusCode.InternalServerError -> {
                  Result.failure(Exception("Server Error: Try again later"))
              }

              else -> {
                  Result.failure(Exception("Unexpected error: ${response.status}"))
              }
          }


      } catch (e: Exception) {
            appLog(AppLogLevel.ERROR, "assessPlantHealth failed: ${e.message}")
          //  null
            Result.failure(e)

        }
    }
}
