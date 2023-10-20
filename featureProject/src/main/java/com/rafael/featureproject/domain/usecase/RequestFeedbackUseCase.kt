package com.rafael.featureproject.domain.usecase

import com.rafael.core.datasource.model.ProjectStepRequest
import com.rafael.core.datasource.model.ProjectStepTypeResponse
import com.rafael.core.extensions.mapSuccess
import com.rafael.featureproject.domain.repository.ProjectRepository

class RequestFeedbackUseCase(val repository: ProjectRepository) {
    suspend operator fun invoke(projectId: String): Result<Unit> {
        return repository.addStep(
            ProjectStepRequest(
                text = "Por favor, forneça o feedback sobre o projecto",
                type = ProjectStepTypeResponse.FEEDBACK_REQUEST,
                project = projectId
            )
        ).mapSuccess { }
    }
}