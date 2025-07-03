package org.example.project.data.fakeData
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import org.example.project.data.model.HealthAssessmentResponse
import org.example.project.data.remote.ApiRepositoryInterface
import org.example.project.data.model.PlantIdentificationResult
import org.example.project.data.model.RequestModel
import org.example.project.utils.loadJsonFromResources

class FakeApiRepository() : ApiRepositoryInterface
{
    override suspend fun getPlantIdentification(request: RequestModel): PlantIdentificationResult {
        println("FakePlantRepository -> getPlantIdentification called with: $request")
        delay(500)
        val json = loadJsonFromResources("mock_plant_response.json")
        return Json { ignoreUnknownKeys = true }.decodeFromString(json)
    }
    override suspend fun getHealthAssessment(request: RequestModel): HealthAssessmentResponse {
        println("FakePlantRepository -> getHealthAssessment called with: $request")
        delay(500)
        val json = loadJsonFromResources("mock_health_response.json")
        return Json { ignoreUnknownKeys = true }.decodeFromString(json)
    }
}
