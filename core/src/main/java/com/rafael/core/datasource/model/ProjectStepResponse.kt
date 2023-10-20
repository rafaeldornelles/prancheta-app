package com.rafael.core.datasource.model

import com.google.gson.annotations.SerializedName
import com.rafael.core.model.ConstructionVisitation
import java.util.Date

data class ProjectStepResponse(
    val _id: String,
    val text: String,
    val imgs: List<String>,
    val type: ProjectStepTypeResponse,
    val project: String,
    val date: Date
)

data class ProjectStepRequest(
    val text: String,
    val type: ProjectStepTypeResponse,
    val project: String,
    val imgs: List<String>? = null,
)

enum class ProjectStepTypeResponse {
    @SerializedName("visitation")
    VISITATION,

    @SerializedName("feedbackrequest")
    FEEDBACK_REQUEST,

    @SerializedName("feedbackresponse")
    FEEDBACK_RESPONSE;
}

fun ProjectStepResponse.toConstructionVisitation() = ConstructionVisitation(
    id = _id,
    date = date.time,
    observation = text,
    images = imgs
)