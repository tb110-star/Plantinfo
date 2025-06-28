package org.example.project.data.remote

import org.example.project.data.model.HealthAssessmentResponse
import org.example.project.data.model.PlantIdentificationResult

interface PlantRepository {
    suspend fun getPlantIdentification(): PlantIdentificationResult
    suspend fun getHealthAssessment(): HealthAssessmentResponse

}