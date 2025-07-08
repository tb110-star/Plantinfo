package org.example.project.data.remote

import org.example.project.data.model.HealthAssessmentResponse
import org.example.project.data.model.PlantIdentificationResult
import org.example.project.data.model.RequestModel

interface ApiRepositoryInterface {
    suspend fun getPlantIdentification(request: RequestModel):  Result<PlantIdentificationResult>
    suspend fun getHealthAssessment(request: RequestModel): Result<HealthAssessmentResponse>

}