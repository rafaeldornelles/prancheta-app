package com.rafael.core.datasource.model

import com.google.gson.annotations.SerializedName
import com.rafael.core.model.BriefingQuestion

data class DefaultBriefingResponse(
    @SerializedName("_id") val id: String,
    val name: String,
    val description: String?,
    val questions: List<QuestionResponse>,
    val user: String
)

data class DefaultBriefingRequest(
    @SerializedName("_id") val id: String,
    val name: String,
    val description: String?,
    val questions: List<BriefingQuestion>
)