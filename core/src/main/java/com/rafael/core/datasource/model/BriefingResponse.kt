package com.rafael.core.datasource.model

import com.google.gson.annotations.SerializedName
import com.rafael.core.model.BriefingForm
import com.rafael.core.model.toBriefingFormQuestion
import java.util.Date

data class BriefingResponse(
    val _id: String,
    val sender: String,
    val client: ClientResponse,
    val questions: List<QuestionResponse>,
    val answeredAt: Date?,
    val sendedAt: Date,
    val project: String
)

data class BriefingRequest(
    val client: ClientResponse,
    val questions: List<QuestionRequest>
)

data class QuestionRequest(
    val questionType: QuestionTypeResponse,
    val caput: String,
    val options: List<QuestionOption>?,
    val placeholder: String?,
    val trailingText: String?
)

data class ClientResponse(
    val name: String,
    val email: String
)

data class QuestionResponse(
    val _id: String,
    val questionType: QuestionTypeResponse,
    val caput: String,
    val options: List<QuestionOption>?,
    val answer: String?,
    val placeholder: String?,
    val trailingText: String?
)

enum class QuestionTypeResponse(val value: String) {
    @SerializedName("text")
    TEXT("text"),

    @SerializedName("number")
    NUMBER("number"),

    @SerializedName("currency")
    CURRENCY("currency"),

    @SerializedName("yesno")
    YESNO("yesno"),

    @SerializedName("multiple")
    MULTIPLE("multiple"),

    @SerializedName("single")
    SINGLE("single");
}

data class QuestionOption(
    val text: String,
    val image: String?
)

fun BriefingResponse.toBriefingForm(): BriefingForm = BriefingForm(
    id = _id,
    clientName = client.name,
    clientEmail = client.email,
    architectId = sender,
    questions = questions.map { it.toBriefingFormQuestion() },
    sendedAt = sendedAt,
    answerTime = answeredAt?.time,
    projectId = project
)
