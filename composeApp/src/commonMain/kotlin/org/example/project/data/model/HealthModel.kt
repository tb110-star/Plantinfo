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
  //  val input: HealthInput? = null,
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
    val suggestions: List<DiseaseSuggestion>,
    val question: Questions
)
@Serializable
data class Questions(
    val text:String,
    val translation: String?= "",
    val options: QuestionOptions

)
@Serializable
data class QuestionOptions(
    val yes: QuestionOptionDetail,
    val no: QuestionOptionDetail
)

@Serializable
data class QuestionOptionDetail(
    @SerialName("suggestion_index")
    val suggestionIndex: Int,
    @SerialName("entity_id")
    val entityId: String,
    val name: String,
    val translation: String?
)
@Serializable
data class DiseaseSuggestion(
    val id: String,
    val name: String,
    val probability: Double,
    @SerialName("similar_images")
    val similarImages: List<SimilarImages>,
    @SerialName("details")
    val details: HealthDetails
    )

@Serializable
data class SimilarImages(
    val id: String,
    val url: String,
    @SerialName("license_name")
    val licenseName: String,
    @SerialName("license_url")
    val licenseUrl: String,
    val citation: String,
    val similarity: Double,
    @SerialName("url_small")
    val urlSmall: String
)

@Serializable
data class HealthDetails(
    @SerialName("local_name")
    val localName:String,
    val description: String? = "",
    val url:String?= "",
    val treatment: Treatment?,
    val classification: List<String>? = emptyList(),
    @SerialName("common_names")
    val commonName: List<String>? = emptyList(),
    val cause: List<String>? = emptyList(),
    @SerialName("entity_id")
    val entityId : String
    )
@Serializable
data class Treatment(
    val chemical: List<String>? = emptyList(),
    val biological: List<String>? = emptyList(),
    val prevention: List<String>? = emptyList(),

)