package org.example.project.data.fakeData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import org.example.project.data.model.HealthAssessmentResponse
import org.example.project.data.remote.PlantRepository
import org.example.project.data.model.PlantIdentificationResult
import org.example.project.utils.loadJsonFromResources

class FakePlantRepository() : PlantRepository
{
    override suspend fun getPlantIdentification(): PlantIdentificationResult {
        val json = loadJsonFromResources("mock_plant_response.json")
        return Json { ignoreUnknownKeys = true }.decodeFromString(json)
    }
    override suspend fun getHealthAssessment(): HealthAssessmentResponse {
        val json = loadJsonFromResources("mock_health_response.json")
        return Json { ignoreUnknownKeys = true }.decodeFromString(json)
    }
}
