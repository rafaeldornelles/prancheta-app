package com.rafael.featureproject.domain.usecase

import com.rafael.core.datasource.model.ProjectStepRequest
import com.rafael.core.datasource.model.ProjectStepTypeResponse
import com.rafael.core.extensions.mapSuccess
import com.rafael.core.model.ConstructionVisitation
import com.rafael.featureproject.domain.repository.ProjectRepository

class AddVisitationUseCase(
    private val repository: ProjectRepository
) {
    suspend operator fun invoke(
        projectId: String,
        visitation: ConstructionVisitation
    ) : Result<Unit> {
        val step = ProjectStepRequest(
            text = visitation.observation,
            type = ProjectStepTypeResponse.VISITATION,
            project = projectId,
            imgs = visitation.images
        )
        return repository.addStep(step).mapSuccess { }
    }
}