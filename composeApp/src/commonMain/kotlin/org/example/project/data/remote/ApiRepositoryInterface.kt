package org.example.project.data.remote

import org.example.project.data.model.HealthAssessmentResponse
import org.example.project.data.model.PlantIdentificationResult
import org.example.project.data.model.RequestModel

interface ApiRepositoryInterface {
    suspend fun getPlantIdentification(request: RequestModel): PlantIdentificationResult
    suspend fun getHealthAssessment(request: RequestModel): HealthAssessmentResponse

}