package com.rafael.core.model

import com.rafael.core.datasource.model.DefaultBriefingRequest
import com.rafael.core.datasource.model.DefaultBriefingResponse

data class DefaultBriefing(
    val id: String,
    val name: String,
    val description: String?,
    val questions: List<BriefingQuestion>
)

fun DefaultBriefingResponse.toDefaultBriefing() = DefaultBriefing(
    id = id,
    name = name,
    description = description,
    questions = questions.mapIndexed { i, b -> b.toBriefingQuestion(i) }
)

fun DefaultBriefing.toDefaultBriefingRequest() = DefaultBriefingRequest(
    id = id,
    name = name,
    description = description,
    questions = questions
)