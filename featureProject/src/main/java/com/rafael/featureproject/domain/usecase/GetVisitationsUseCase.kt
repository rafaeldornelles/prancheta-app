package com.rafael.featureproject.domain.usecase

import com.rafael.core.datasource.model.ProjectStepTypeResponse
import com.rafael.core.datasource.model.toConstructionVisitation
import com.rafael.core.extensions.mapSuccess
import com.rafael.core.model.ConstructionVisitation
import com.rafael.featureproject.domain.repository.ProjectRepository

class GetVisitationsUseCase(
    private val repository: ProjectRepository
) {
    suspend operator fun invoke(projectId: String) : Result<List<ConstructionVisitation>> {
        return repository.getProject(projectId).mapSuccess {
            it.steps.filter { it.type == ProjectStepTypeResponse.VISITATION }.map {
                it.toConstructionVisitation()
            }
        }
    }
}