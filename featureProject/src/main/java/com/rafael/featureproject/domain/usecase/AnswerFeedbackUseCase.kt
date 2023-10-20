package com.rafael.featureproject.domain.usecase

import com.rafael.core.datasource.endpoints.ProjectEndpoint
import com.rafael.core.datasource.model.ProjectStepRequest
import com.rafael.core.datasource.model.ProjectStepTypeResponse
import com.rafael.core.extensions.mapSuccess

class AnswerFeedbackUseCase(private val endpoint: ProjectEndpoint) {
    suspend operator fun invoke(projectId: String, feedback: String): Result<Unit> {
        return endpoint.addStep(
            ProjectStepRequest(
                text = feedback,
                type = ProjectStepTypeResponse.FEEDBACK_RESPONSE,
                project = projectId
            )
        ).mapSuccess { }
    }
}