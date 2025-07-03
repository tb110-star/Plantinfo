package org.example.project.data.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class HealthAssessmentResponse(
    @SerialName("access_token")
    val accessToken: String? = null,
    @SerialName("model_version")
    val modelVersion: String? = null,
    @SerialName("custom_id")
    val customId: String? = null,
    @SerialName("input")
    val input: HealthInput ,
    val result: HealthResult,
    val status: String? = null,
    @SerialName("sla_compliant_client")
    val slaCompliantClient: Boolean? = null,
    @SerialName("sla_compliant_system")
    val slaCompliantSystem: Boolean? = null,
    val created: Double? = null,
    val completed: Double? = null
)

@Serializable
data class HealthInput(

    val images: List<String>,
)

@Serializable
data class HealthResult(
    @SerialName("is_healthy")
    val isHealthy: HealthCheck,
    val disease: DiseaseResult,
    @SerialName("is_plant")
    val isPlant: IsItPlant
)
@Serializable
data class IsItPlant(
    val probability: Double,
    val threshold: Double,
    val binary: Boolean,
)
@Serializable
data class HealthCheck(
    val binary: Boolean,
    val threshold: Double,
    val probability: Double
)
@Serializable
data class DiseaseResult(
    val suggestions: List<DiseaseSuggestion>? = emptyList(),
    val question: Questions? = null
)
@Serializable
data class Questions(
    val text:String? = null,
    val translation: String?= "",
    val options: QuestionOptions? = null

)
@Serializable
data class QuestionOptions(
    val yes: QuestionOptionDetail? = null,
    val no: QuestionOptionDetail? = null
)

@Serializable
data class QuestionOptionDetail(
    @SerialName("suggestion_index")
    val suggestionIndex: Int? = null,
    @SerialName("entity_id")
    val entityId: String? = null,
    val name: String? = null,
    val translation: String? = null
)
@Serializable
data class DiseaseSuggestion(
    val id: String? = null,
    val name: String? = null,
    val probability: Double? = null,
    @SerialName("similar_images")
    val similarImages: List<SimilarImages>? = emptyList(),
    @SerialName("details")
    val details: HealthDetails? = null
    )

@Serializable
data class SimilarImages(
    val id: String? = null,
    val url: String? = null,
    @SerialName("license_name")
    val licenseName: String? = null,
    @SerialName("license_url")
    val licenseUrl: String? = null,
    val citation: String? = null,
    val similarity: Double? = null,
    @SerialName("url_small")
    val urlSmall: String? = null
)

@Serializable
data class HealthDetails(
    @SerialName("local_name")
    val localName:String? = null,
    val description: String? = "",
    val url:String?= "",
    val treatment: Treatment? = null,
    val classification: List<String>? = emptyList(),
    @SerialName("common_names")
    val commonName: List<String>? = emptyList(),
    val cause: List<String>? = emptyList(),
    @SerialName("entity_id")
    val entityId : String? = null
    )
@Serializable
data class Treatment(
    val chemical: List<String>? = emptyList(),
    val biological: List<String>? = emptyList(),
    val prevention: List<String>? = emptyList(),

)