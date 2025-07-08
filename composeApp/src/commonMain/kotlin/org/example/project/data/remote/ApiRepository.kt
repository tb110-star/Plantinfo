package org.example.project.data.remote

import org.example.project.data.model.HealthAssessmentResponse
import org.example.project.data.model.PlantIdentificationResult
import org.example.project.data.model.RequestModel

class ApiRepository(
    private val api: ApiService
) : ApiRepositoryInterface {
    override suspend fun getPlantIdentification(request: RequestModel): Result<PlantIdentificationResult>  {
        return api.identifyPlant(request)
    }

    override suspend fun getHealthAssessment(request: RequestModel):  Result<HealthAssessmentResponse> {
        return api.assessPlantHealth(request)
    }
}
