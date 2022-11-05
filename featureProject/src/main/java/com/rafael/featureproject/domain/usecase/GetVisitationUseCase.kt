package com.rafael.featureproject.domain.usecase

import com.rafael.core.model.ConstructionVisitation
import com.rafael.featureproject.domain.repository.ProjectRepository

class GetVisitationUseCase(
    private val repository: ProjectRepository
) {
    suspend operator fun invoke(projectId: String, visitationId: String) : Result<ConstructionVisitation> {
        return try {
            Result.success(repository.getVisitation(projectId, visitationId)!!)
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }
}