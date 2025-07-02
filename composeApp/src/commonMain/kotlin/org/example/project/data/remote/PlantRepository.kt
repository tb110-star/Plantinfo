package org.example.project.data.remote

import kotlinx.serialization.json.Json
import org.example.project.data.model.HealthAssessmentResponse
import org.example.project.data.model.PlantIdentificationResult
import org.example.project.data.model.RequestModel
import org.example.project.utils.loadJsonFromResources
class PlantRepository(
    private val api: ApiService
) : PlantRepositoryInterface {
    override suspend fun getPlantIdentification(request: RequestModel): PlantIdentificationResult {
        return api.identifyPlant(request)
            ?: throw IllegalStateException("identifyPlant returned null")
    }

    override suspend fun getHealthAssessment(request: RequestModel): HealthAssessmentResponse {
        return api.assessPlantHealth(request)
            ?: throw IllegalStateException("assessPlantHealth returned null")
    }
}
