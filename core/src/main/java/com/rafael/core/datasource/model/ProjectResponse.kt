package com.rafael.core.datasource.model

import com.rafael.core.model.Project
import java.util.Date

data class ProjectResponse(
    val _id: String,
    val name: String,
    val createdAt: Date,
    val user: UserResponse,
    val briefing: BriefingResponse,
    val steps: List<ProjectStepResponse>
)

data class ProjectRequest(
    val name: String,
    val briefing: String
)

fun ProjectResponse.toProject() = Project(
    id = _id,
    clientName = briefing.client.name,
    clientEmail = briefing.client.email,
    architectId = user._id,
    projectStart = createdAt,
    briefing = briefing.toBriefingForm(),
    isConcluded = steps.any { it.type == ProjectStepTypeResponse.FEEDBACK_REQUEST },
    feedback = steps.firstOrNull { it.type == ProjectStepTypeResponse.FEEDBACK_RESPONSE }?.text
)