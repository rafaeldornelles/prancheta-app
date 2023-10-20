package com.rafael.featureproject.domain.usecase

import com.rafael.core.datasource.model.toConstructionVisitation
import com.rafael.core.extensions.mapSuccess
import com.rafael.core.model.ConstructionVisitation
import com.rafael.featureproject.domain.repository.ProjectRepository

class GetVisitationUseCase(
    private val repository: ProjectRepository
) {
    suspend operator fun invoke(projectId: String, visitationId: String) : Result<ConstructionVisitation> {
        return repository.getProject(projectId).mapSuccess {
            it.steps.find { it._id == visitationId }?.toConstructionVisitation() ?: throw Exception(
                "id inválido"
            )
        }
    }
}